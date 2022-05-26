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
import com.github.shoothzj.dev.module.shell.KubectlPodResult;
import com.github.shoothzj.dev.shell.KubectlNodeResultParser;
import com.github.shoothzj.dev.shell.KubectlPodResultParser;
import com.github.shoothzj.dev.state.State;
import com.github.shoothzj.dev.storage.StorageSettings;
import com.github.shoothzj.dev.util.SshClient;
import com.github.shoothzj.dev.util.StringTool;
import com.github.shoothzj.javatool.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class Transfer {
    private static final Logger log = LoggerFactory.getLogger(Transfer.class);

    private static final ExecutorService fixedPool = Executors.newFixedThreadPool(2);

    public TransferResp masterTransfer(String sshUsername, String sshPassword, String host,
                                       int port, String masterFile, String targetPath) {
        SshClient sshClient = null;
        try {
            sshClient = new SshClient(host, port, sshUsername, sshPassword);

            List<String> body = sshClient.execute(K8sCmdConst.GET_NODE_LIST, StorageSettings.getConfig().getSshExecuteTimeoutSeconds());
            List<KubectlNodeResult> nodeResults = KubectlNodeResultParser.parseFull(body);
            List<FutureTask<String>> futureTasks = new ArrayList<>();
            for (KubectlNodeResult nodeResult : nodeResults) {
                FutureTask<String> futureTask = new FutureTask<>(() -> {
                    SshClient client = null;
                    try {
                        client = new SshClient(host, port, sshUsername, sshPassword);
                        client.execute(LinuxCmdConst.scpCmd(masterFile,
                                nodeResult.getInternalIp(), targetPath), StorageSettings.getConfig().getSshExecuteTimeoutSeconds());
                        CommonUtil.sleep(TimeUnit.SECONDS, 5);
                        client.execute(sshPassword, StorageSettings.getConfig().getSshExecuteTimeoutSeconds());
                        CommonUtil.sleep(TimeUnit.SECONDS, 5);
                    } catch (Exception e) {
                        log.error("send file fail. ", e);
                        return String.format("send file to remote fail. IP [%s]", nodeResult.getInternalIp());
                    } finally {
                        if (client != null) {
                            client.close();
                        }
                    }
                    SshClient jumpClient = null;
                    try {
                        jumpClient = new SshClient(host, port, sshUsername, sshPassword);
                        jumpClient.jump(nodeResult.getInternalIp(), sshPassword);
                        List<String> list =
                                jumpClient.execute(LinuxCmdConst.lsCmd(targetPath), StorageSettings.getConfig().getSshExecuteTimeoutSeconds());
                        if (StringTool.anyLineContains(list, targetPath)) {
                            return String.format("send file to remote success. IP [%s]", nodeResult.getInternalIp());
                        } else {
                            log.error("ls failed, content is {}", list);
                            return String.format("send file to remote fail. IP [%s]", nodeResult.getInternalIp());
                        }
                    } catch (Exception e) {
                        log.error("send file fail. ", e);
                        return String.format("send file to remote fail. IP [%s]", nodeResult.getInternalIp());
                    } finally {
                        if (jumpClient != null) {
                            jumpClient.close();
                        }
                    }
                });
                fixedPool.execute(futureTask);
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
            List<String> body = sshClient.execute(K8sCmdConst.GET_NODE_LIST, StorageSettings.getConfig().getSshExecuteTimeoutSeconds());
            List<KubectlNodeResult> nodeResults = KubectlNodeResultParser.parseFull(body);
            List<FutureTask<List<String>>> futureTasks = new ArrayList<>();
            for (KubectlNodeResult nodeResult : nodeResults) {
                FutureTask<List<String>> futureTask = new FutureTask<>(() -> {
                    SshClient client = null;
                    try {
                        client = new SshClient(host, port, sshUsername, sshPassword);
                        client.jump(nodeResult.getInternalIp(), sshPassword);
                        return client.execute(cmd, StorageSettings.getConfig().getSshExecuteTimeoutSeconds());
                    } catch (Exception e) {
                        log.error("execute cmd fail. {} ", cmd, e);
                    } finally {
                        if (client != null) {
                            client.close();
                        }
                    }
                    return new ArrayList<>();
                });
                fixedPool.execute(futureTask);
                futureTasks.add(futureTask);
            }
            List<List<String>> collect = futureTasks.stream().map(futureTask -> {
                try {
                    return futureTask.get();
                } catch (Exception e) {
                    log.error("execute cmd fail. ", e);
                    return new ArrayList<String>();
                }
            }).toList();
            ArrayList<NodeInfo> nodeInfoList = new ArrayList<>();
            for (int i = 0; i < nodeResults.size(); i++) {
                NodeInfo nodeInfo = new NodeInfo();
                nodeInfo.setName(nodeResults.get(i).getInternalIp());
                nodeInfo.setExecuteResult(collect.get(i));
                nodeInfoList.add(nodeInfo);
            }
            return new TransferResp(State.HASCONTENT.getCode(), new ArrayList<>(), nodeInfoList, "");
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
            List<String> body = sshClient.execute(K8sCmdConst.GET_NODE_LIST, StorageSettings.getConfig().getSshExecuteTimeoutSeconds());
            List<KubectlNodeResult> nodeResults = KubectlNodeResultParser.parseFull(body);
            return nodeResults.stream()
                    .map(result -> String.format("name=%s, status=%s, internalIp=%s",
                            result.getName(), result.getStatus(), result.getInternalIp()))
                    .collect(Collectors.toList());
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

    public List<String> getPodsInfo(String sshUsername, String sshPassword, String host, int port, String namespace) {
        SshClient sshClient = null;
        try {
            sshClient = new SshClient(host, port, sshUsername, sshPassword);
            List<String> body = sshClient.execute(K8sCmdConst.podListCmd(namespace),
                    StorageSettings.getConfig().getSshExecuteTimeoutSeconds());
            List<KubectlPodResult> podResults = KubectlPodResultParser.parseFull(body);
            return podResults.stream()
                    .map(result -> String.format("podName=%s, status=%s, Ip=%s",
                            result.getPodName(), result.getStatus(), result.getIp()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("get pod info fail. ", e);
            List<String> res = new ArrayList<>();
            res.add(String.format("get pod info fail. host [%s]", host));
            return res;
        } finally {
            if (sshClient != null) {
                sshClient.close();
            }
        }
    }

    public List<String> getNamespaceInfo(String sshUsername, String sshPassword, String host, int port) {
        SshClient sshClient = null;
        try {
            sshClient = new SshClient(host, port, sshUsername, sshPassword);
            List<String> body = sshClient.execute(K8sCmdConst.GET_PODS_LIST,
                    StorageSettings.getConfig().getSshExecuteTimeoutSeconds());
            HashSet<String> namespaces = new HashSet<>();
            for (String s : body) {
                namespaces.add(s.split(" ")[0]);
            }
            return new ArrayList<>(namespaces);
        } catch (Exception e) {
            log.error("get namespaces info fail. ", e);
            List<String> res = new ArrayList<>();
            res.add(String.format("get namespaces info fail. host [%s]", host));
            return res;
        } finally {
            if (sshClient != null) {
                sshClient.close();
            }
        }
    }

    public TransferResp replaceWord(String sshUsername, String sshPassword, String host, int port,
                                    String resource, String target, String filename) {
        String cmd = LinuxCmdConst.sedCmd(resource, target, filename);
        TransferResp transferResp = execute(sshUsername, sshPassword, host, port, cmd);
        List<NodeInfo> collect = transferResp.getNodeInfos().stream().peek(nodeInfo -> {
            if (nodeInfo.getExecuteResult().isEmpty()) {
                List<String> res = new ArrayList<>();
                res.add("replace success.");
                nodeInfo.setExecuteResult(res);
            }
        }).collect(Collectors.toList());
        transferResp.setNodeInfos(collect);
        return transferResp;
    }
}
