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

package com.github.shoothzj.dev.util;

import com.github.shoothzj.dev.module.SshShellResult;
import com.github.shoothzj.javatool.util.CommonUtil;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;

public class SshUtil {

    private static final Logger log = LoggerFactory.getLogger(SshUtil.class);

    public static SshShellResult execCommand(String host, int port,
                                             String username, String password, String command) throws Exception {
        final SshShellResult shellResult = new SshShellResult();
        Session session = null;
        ChannelExec channel = null;
        try {
            session = new JSch().getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ByteArrayOutputStream errStream = new ByteArrayOutputStream();
            channel.setOutputStream(outputStream);
            channel.setErrStream(errStream);
            channel.connect();
            while (!channel.isClosed()) {
                CommonUtil.sleep(TimeUnit.MILLISECONDS, 100);
            }
            shellResult.setOutputContent(outputStream.toString());
            shellResult.setErrorContent(errStream.toString());
            shellResult.setReturnCode(channel.getExitStatus());
            log.info("exec code is {}", shellResult.getReturnCode());
            log.info("exec err result is {}", shellResult.getErrorContent());
            log.info("exec output result is {}", shellResult.getOutputContent());
        } finally {
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
        return shellResult;
    }

}
