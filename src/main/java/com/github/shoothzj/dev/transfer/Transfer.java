/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.github.shoothzj.dev.transfer;

import com.github.shoothzj.dev.constant.K8sCmdConst;
import com.github.shoothzj.dev.constant.LinuxCmdConst;
import com.github.shoothzj.dev.module.shell.KubectlNodeResult;
import com.github.shoothzj.dev.shell.KubectlNodeResultParser;
import com.github.shoothzj.dev.state.State;
import com.github.shoothzj.dev.util.SshClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;


public class Transfer {

    private static final Logger log = LoggerFactory.getLogger(Transfer.class);

    public TransferResp masterTransfer(String sshUsername, String sshPassword, String host,
                                       int port, String masterFile, String targetPath) {
        ExecutorService fixedPool = Executors.newFixedThreadPool(10);
        SshClient sshClient = null;
        try {
            sshClient = new SshClient(host, port, sshUsername, sshPassword);

            List<String> body = sshClient.execute(K8sCmdConst.GET_NODE_LIST, 5);
            List<KubectlNodeResult> nodeResults = KubectlNodeResultParser.parseBody(body);
            List<FutureTask<String>> futureTasks = new ArrayList<>();
            for (KubectlNodeResult nodeResult : nodeResults) {
                FutureTask<String> futureTask = new FutureTask<>(() -> {
                    SshClient client = null;
                    SshClient jumpClient = null;
                    try {
                        client = new SshClient(host, port, sshUsername, sshPassword);
                        jumpClient = new SshClient(host, port, sshUsername, sshPassword);
                        client.execute(LinuxCmdConst.scpCmd(masterFile, nodeResult.getExternalIp(), targetPath), 3);
                        Thread.sleep(7_000);
                        client.execute("yes", 15);
                        client.execute(sshPassword, 20);
                        Thread.sleep(7_000);
                        jumpClient.jump(nodeResult.getExternalIp(), sshPassword);
                        String[] fields = masterFile.split("/");
                        List<String> list = jumpClient.execute(LinuxCmdConst.lsCmd(fields[fields.length - 1]), 5);
                        if (list.size() == 0) {
                            return String.format("send file to remote success. IP [%s]", nodeResult.getInternalIp());
                        } else {
                            return String.format("send file to remote fail. IP [%s]", nodeResult.getInternalIp());
                        }
                    } catch (Exception e) {
                        log.error("send file fail. ", e);
                        return String.format("send file to remote fail. IP [%s]", nodeResult.getInternalIp());
                    } finally {
                        if (client != null) {
                            client.close();
                        }
                        if (jumpClient != null) {
                            jumpClient.close();
                        }
                    }
                });
                futureTasks.add(futureTask);
            }
            List<String> content = futureTasks.stream().map(futureTask -> {
                try {
                    return futureTask.get();
                } catch (Exception e) {
                    log.error("master transfer fail.", e);
                    return "";
                }
            }).collect(Collectors.toList());
            return new TransferResp(State.HASCONTENT.getCode(),
                    content, new ArrayList<>(), "send file to virtual machine success.");
        } catch (Exception e) {
            log.error("master transfer fail.", e);
            return new TransferResp(State.NOCONTENT.getCode(),
                    new ArrayList<>(), new ArrayList<>(), "send file to virtual machine fail.");
        } finally {
            fixedPool.shutdown();
            if (sshClient != null) {
                sshClient.close();
            }
        }

    }

    public TransferResp localTransfer(String sshUsername, String sshPassword, String host,
                                      int port, String localFile, String targetPath) {
        SshClient sshClient = null;
        try {
            sshClient = new SshClient(host, port, sshUsername, sshPassword);
            sshClient.sftp(localFile, targetPath);
            return new TransferResp(State.NOCONTENT.getCode(), new ArrayList<>(), new ArrayList<>(), "success");
        } catch (Exception e) {
            log.error("send file to remote machine fail. ", e);
            return new TransferResp(State.NOCONTENT.getCode(), new ArrayList<>(), new ArrayList<>(), "fail.");
        } finally {
            if (sshClient != null) {
                sshClient.close();
            }
        }
    }

    public TransferResp execute(String sshUsername, String sshPassword, String host, int port, String cmd) {
        SshClient sshClient = null;
        try {
            sshClient = new SshClient(host, port, sshUsername, sshPassword);
            List<String> body = sshClient.execute(K8sCmdConst.GET_NODE_LIST, 5);
            List<KubectlNodeResult> nodeResults = KubectlNodeResultParser.parseBody(body);
            nodeResults.remove(0);
            List<FutureTask<List<String>>> futureTasks = new ArrayList<>();
            for (KubectlNodeResult nodeResult : nodeResults) {
                FutureTask<List<String>> futureTask = new FutureTask<>(() -> {
                    SshClient client = null;
                    try {
                        client = new SshClient(host, port, sshUsername, sshPassword);
                        client.jump(nodeResult.getInternalIp(), sshPassword);
                        return client.execute(cmd, 10);
                    } catch (Exception e) {
                        log.error("execute cmd fail. {} ", cmd, e);
                    } finally {
                        if (client != null) {
                            client.close();
                        }
                    }
                    return new ArrayList<>();
                });
                new Thread(futureTask).start();
                futureTasks.add(futureTask);
            }
            List<List<String>> collect = futureTasks.stream().map(futureTask -> {
                try {
                    return futureTask.get();
                } catch (Exception e) {
                    log.error("execute cmd fail. ", e);
                    return new ArrayList<String>();
                }
            }).collect(Collectors.toList());
            ArrayList<NodeInfo> nodeInfos = new ArrayList<>();
            for (int i = 0; i < nodeResults.size(); i++) {
                NodeInfo nodeInfo = new NodeInfo();
                nodeInfo.setName(nodeResults.get(i).getInternalIp());
                nodeInfo.setExecuteResult(collect.get(i));
                nodeInfos.add(nodeInfo);
            }
            return new TransferResp(State.HASCONTENT.getCode(), new ArrayList<>(), nodeInfos, "");
        } catch (Exception e) {
            return new TransferResp(State.NOCONTENT.getCode(),
                    new ArrayList<>(), new ArrayList<>(), String.format("fail to connector remote. host [%s]", host));
        } finally {
            if (sshClient != null) {
                sshClient.close();
            }
        }
    }

    public List<String> getNodeInfo(String sshUsername, String sshPassword, String host, int port) {
        SshClient sshClient = null;
        try {
            sshClient = new SshClient(host, port, sshUsername, sshPassword);
            List<String> body = sshClient.execute(K8sCmdConst.GET_NODE_LIST, 5);
            List<KubectlNodeResult> nodeResults = KubectlNodeResultParser.parseBody(body);
            List<String> list = nodeResults.stream()
                    .map(result -> String.format("name=%s, status=%s, internalIp=%s",
                            result.getName(), result.getStatus(), result.getInternalIp()))
                    .collect(Collectors.toList());
            list.remove(0);
            return list;
        } catch (Exception e) {
            log.error("get node info fail. ", e);
            List<String> res = new ArrayList<>();
            res.add(String.format("get node info fail. host [%s]", host));
            return res;
        } finally {
            if (sshClient != null) {
                sshClient.close();
            }
        }
    }
}
