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

import com.github.shoothzj.dev.constant.Constant;
import com.github.shoothzj.dev.constant.K8sCmdConst;
import com.github.shoothzj.dev.storage.StorageSettings;
import com.github.shoothzj.dev.util.SshClient;
import com.github.shoothzj.javatool.util.ExceptionUtil;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DumpAction {

    private static final Logger log = LoggerFactory.getLogger(DumpAction.class);

    public String dump(String host, int port, String username, String password, String podName, String namespace, String pid) {
        try {
            SshClient sshClient = new SshClient(host, port, username, password);
            List<String> vmArch = sshClient.execute(K8sCmdConst.execPodCmd(namespace, podName, "uname -m"), 5);
            if (vmArch.isEmpty()) {
                return "get vm arch failed.";
            }
            List<String> execute = sshClient.execute(K8sCmdConst.execPodCmd(namespace, podName, "java -version"), 5);
            if (execute.size() == 0) {
                return "this pod is not a java instance.";
            }
            String jdkVersion = execute.get(0).split(" ")[2].replace("\r", "").replace("\"", "");
            String jdkPath = getJdkPath(vmArch.get(0), jdkVersion);
            if (StringUtil.isNullOrEmpty(jdkPath)) {
                return "jdk path is not setting";
            }
            sshClient.sftp(jdkPath, "/root/");
            log.info("success to send jdk to remote.");
            String jdkCompressionFileName = getJdkCompressionFileName(jdkPath);
            String jdkFileName = "jdk" + jdkVersion;
            sshClient.execute("mkdir /root/" + jdkFileName, 5);
            sshClient.execute("tar -zxvf /root/" + jdkCompressionFileName + " --strip-components 1 -C /root/" + jdkFileName, 120);
            sshClient.execute("kubectl cp /root/" + jdkFileName + " " + podName + ":/tmp/", 120);

            sshClient.execute(K8sCmdConst.execPodCmd(namespace, podName, "/tmp/" + jdkFileName + "/bin/jmap -dump:live,format=b,file=/tmp/" + podName + ".hprof " + pid), 120);
            sshClient.execute(K8sCmdConst.execPodCmd(namespace, podName, "tar -zvcf /tmp/" + podName + ".tar.gz" + " /tmp/" + podName + ".hprof"), 120);
            sshClient.execute("kubectl cp " + podName + ":/tmp/" + podName + ".tar.gz /root/" + podName + ".tar.gz", 120);
            sshClient.sftpFromRemote("/root/" + podName + ".tar.gz", getDumpDirName());
        } catch (Exception e) {
            log.error("dump failed. e: {}", ExceptionUtil.getException(e));
            return "dump failed. e :" + e.getMessage();
        }
        return "dump file success.";
    }


    private String getJdkPath(String vmArch, String jdkVersion) {
        String jdkPath = "";
        if (vmArch.startsWith("x86")) {
            if (jdkVersion.startsWith("1.8")) {
                jdkPath = StorageSettings.getConfig().getJdk8X86FilePath();
            }
            if (jdkVersion.startsWith("11")) {
                jdkPath = StorageSettings.getConfig().getJdk11X86FilePath();
            }
            if (jdkVersion.startsWith("17")) {
                jdkPath = StorageSettings.getConfig().getJdk17X86FilePath();
            }
        } else if (vmArch.startsWith("aarch64")) {
            if (jdkVersion.startsWith("1.8")) {
                jdkPath = StorageSettings.getConfig().getJdk8armFilePath();
            }
            if (jdkVersion.startsWith("11")) {
                jdkPath = StorageSettings.getConfig().getJdk11armFilePath();
            }
            if (jdkVersion.startsWith("17")) {
                jdkPath = StorageSettings.getConfig().getJdk17armFilePath();
            }
        }
        return jdkPath;
    }

    private String getJdkCompressionFileName(String jdkFilePath) {
        return jdkFilePath.substring(jdkFilePath.lastIndexOf("/") + 1);
    }

    private String getDumpDirName() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constant.DEFAULT_TIME_FORMAT);
        String dumpDir = StorageSettings.getConfig().getDumpFileDir() + "/" + formatter.format(LocalDateTime.now());
        File file = new File(dumpDir);
        file.mkdir();
        return dumpDir;
    }
}
