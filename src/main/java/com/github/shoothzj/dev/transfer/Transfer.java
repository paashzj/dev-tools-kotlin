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

import java.util.List;


public class Transfer {

    private static final Logger log = LoggerFactory.getLogger(Transfer.class);

    public String transferFile(String sshUsername, String sshPassword, String host,
                               String port, String localFile, String targetPath) {

        try {
            ScpClient.builder().build();
            ScpClient.Client scpClient = ScpClient.builder()
                    .setHost(host).setPort(Integer.parseInt(port))
                    .setUsername(sshUsername).setPassword(sshPassword).build();

            boolean isSuccess = scpClient.scpFile(localFile, targetPath);
            if (isSuccess) {
                SshClient sshClient = new SshClient(host, Integer.parseInt(port), sshUsername, sshPassword);
                List<String> body = sshClient.execute(K8sCmdConst.GET_NODE_LIST, 5);
                List<KubectlNodeResult> nodeResults = KubectlNodeResultParser.parseBody(body);
                for (KubectlNodeResult nodeResult : nodeResults) {
                    sshClient.execute(LinuxCmdConst.scpCmd(localFile, nodeResult.getExternalIp(), targetPath), 3);
                    sshClient.execute("yes", 15);
                    sshClient.execute(sshPassword, 20);
                }
            }
            return "send file to virtual machine success.";
        } catch (Exception e) {
            log.error("fail to login virtual machine. host[{}] port[{}] username[{}] password[{}]"
                    , host, port, sshUsername, sshPassword, e);
            return "send file to virtual machine fail.";
        }
    }
}
