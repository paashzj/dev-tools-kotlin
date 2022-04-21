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

import com.github.shoothzj.dev.client.ScpClient;
import com.github.shoothzj.dev.constant.K8sCmdConst;
import com.github.shoothzj.dev.constant.LinuxCmdConst;
import com.github.shoothzj.dev.module.shell.KubectlNodeResult;
import com.github.shoothzj.dev.shell.KubectlNodeResultParser;
import com.github.shoothzj.dev.util.SshClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Transfer {

    private static final Logger log = LoggerFactory.getLogger(Transfer.class);

    public TransferResp masterTransfer(String sshUsername, String sshPassword, String host,
                                       int port, String localFile, String targetPath) {
        SshClient sshClient = null;
        try {
            sshClient = new SshClient(host, port, sshUsername, sshPassword);
            List<String> body = sshClient.execute(K8sCmdConst.GET_NODE_LIST, 5);
            List<KubectlNodeResult> nodeResults = KubectlNodeResultParser.parseBody(body);
            for (KubectlNodeResult nodeResult : nodeResults) {
                new Thread(() -> {
                    SshClient client = null;
                    try {
                        client = new SshClient(host, port, sshUsername, sshPassword);
                        client.execute(LinuxCmdConst.scpCmd(localFile, nodeResult.getExternalIp(), targetPath), 3);
                        Thread.sleep(7_000);
                        client.execute("yes", 15);
                        client.execute(sshPassword, 20);
                    } catch (Exception e) {
                        log.error("send file fail. ", e);
                    } finally {
                        if (client != null) {
                            client.close();
                        }
                    }
                }).start();
            }
            return new TransferResp(400, null, "send file to virtual machine success.");
        } catch (Exception e) {
            log.error("master transfer fail.", e);
            return new TransferResp(400, null, "send file to virtual machine fail.");
        } finally {
            if (sshClient != null) {
                sshClient.close();
            }
        }

    }

    public TransferResp localTransfer(String sshUsername, String sshPassword, String host,
                                      int port, String localFile, String targetPath) {
        try {
            ScpClient.Client scpClient = ScpClient.builder()
                    .setHost(host).setPort(port)
                    .setUsername(sshUsername).setPassword(sshPassword).build();
            boolean isSuccess = scpClient.scpFile(localFile, targetPath);
            return new TransferResp(400, null, String.valueOf(isSuccess));
        } catch (Exception e) {
            log.error("send file to remote machine fail. ", e);
            return new TransferResp(400, null, "fail.");
        }
    }

    public TransferResp execute(String sshUsername, String sshPassword, String host,
                                int port, String cmd) {
        SshClient client = null;
        try {
            client = new SshClient(host, port, sshUsername, sshPassword);
            List<String> res = client.execute(cmd, 10);
            return new TransferResp(200, res, "");
        } catch (Exception e) {
            return new TransferResp(400, null, "execute fail.");
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

    public List<String> getNodeInfo(String sshUsername, String sshPassword, String host, int port) {
        SshClient sshClient = null;
        try {
            sshClient = new SshClient(host, port, sshUsername, sshPassword);
            List<String> body = sshClient.execute(K8sCmdConst.GET_NODE_LIST, 5);
            List<KubectlNodeResult> nodeResults = KubectlNodeResultParser.parseBody(body);
            List<String> list = nodeResults.stream().map(KubectlNodeResult::toString)
                    .map(info -> info.substring(18, info.length() - 1)).collect(Collectors.toList());
            return list;
        } catch (Exception e) {
            log.error("get node info fail. ", e);
            return new ArrayList<>();
        } finally {
            if (sshClient != null) {
                sshClient.close();
            }
        }
    }
}
