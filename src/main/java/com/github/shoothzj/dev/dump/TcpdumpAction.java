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

package com.github.shoothzj.dev.dump;

import com.github.shoothzj.dev.constant.DockerCmdConst;
import com.github.shoothzj.dev.constant.K8sCmdConst;
import com.github.shoothzj.dev.storage.StorageSettings;
import com.github.shoothzj.dev.util.SshClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TcpdumpAction {

    private static final Logger log = LoggerFactory.getLogger(TcpdumpAction.class);

    public String installTcpdump(String host, int port, String username, String password, String podName, String namespace, String vmHost, String vmPassword) {
        try {
            SshClient sshClient = new SshClient(host, port, username, password);
            List<String> vmArch = sshClient.execute(K8sCmdConst.execPodCmd(namespace, podName, "uname -m"), 5);
            if (vmArch.isEmpty()) {
                return "get vm arch failed.";
            }
            String tcpdumpPath = getTcpdumpPath(vmArch.get(0));
            String tcpdumpFile = getTcpDumpCompressionFileName(tcpdumpPath);
            log.info("tcpdump file is : {}", tcpdumpFile);
            sshClient.sftp(tcpdumpPath, "/root/");
            sshClient.execute("mkdir tcpdump", 5);
            sshClient.execute("tar -zxvf /root/" + tcpdumpFile + " -C /root/tcpdump/", 5);
            sshClient.execute(K8sCmdConst.cpFile("/root/tcpdump", podName + ":/tmp/", namespace), 5);
            List<String> fileList = sshClient.execute(K8sCmdConst.execPodCmd(namespace, podName, "ls -lrt /tmp/tcpdump"), 5);
            String libpFileName = "";
            String tcpdumpFileName = "";
            for (String s : fileList) {
                String[] arr = s.split(" ");
                if (s.contains("libpcap")) {
                    libpFileName = arr[arr.length - 1];
                }
                if (s.contains("tcpdump")) {
                    tcpdumpFileName = arr[arr.length - 1];
                }
            }
            log.info("libpcap file name is : {}, tcpdump file name is : {}", libpFileName, tcpdumpFileName);
            sshClient.jump(vmHost, vmPassword);
            List<String> dockerList = sshClient.execute("docker ps | grep " + podName, 5);
            String dockerId = "";
            for (String s : dockerList) {
                if (s.contains("pause")) {
                    continue;
                }
                String[] arr = s.split(" ");
                dockerId = arr[0];
                break;
            }
            sshClient.execute(DockerCmdConst.execDocker("root", dockerId, "rpm -ivh /tmp/tcpdump/" + libpFileName), 5);
            sshClient.execute(DockerCmdConst.execDocker("root", dockerId, "rpm -ivh /tmp/tcpdump/" + tcpdumpFileName), 5);
            return "install tcpdump end";
        } catch (Exception e) {
            log.error("install tcpdump failed. e: {}", e.getMessage());
        }
        return "install tcpdump failed.";
    }

    private String getTcpdumpPath(String vmArch) {
        String tcpDumpPath = "";
        if (vmArch.startsWith("x86")) {
            tcpDumpPath = StorageSettings.getConfig().getTcpdumpX86FilePath();
        } else if (vmArch.startsWith("aarch64")) {
            tcpDumpPath = StorageSettings.getConfig().getTcpdumpArmFilePath();
        }
        return tcpDumpPath;
    }

    private String getTcpDumpCompressionFileName(String tcpdumpFile) {
        return tcpdumpFile.substring(tcpdumpFile.lastIndexOf("/") + 1);
    }
}
