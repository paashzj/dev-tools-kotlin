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

import com.github.shoothzj.javatool.util.CommonUtil;
import com.github.shoothzj.javatool.util.IoUtil;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SshClient {

    private final JSch jSch;

    private Session session;

    private ChannelShell channel;

    private InputStream inputStream;

    private OutputStream outputStream;

    public SshClient(String host, int port, String username, String password) {
        this.jSch = new JSch();
    }

    private void login(String host, int port, String username, String password) throws Exception {
        session = jSch.getSession(username, host, port);
        session.setPassword(password);
        Properties properties = new Properties();
        properties.setProperty("StrictHostKeyChecking", "no");
        session.setConfig(properties);
        session.connect();
        channel = (ChannelShell) session.openChannel("shell");
        channel.connect(15_000);
        inputStream = channel.getInputStream();
        outputStream = channel.getOutputStream();
    }

    public String execute(String cmd, int timeoutSeconds) throws Exception {
        return execute(cmd, "\\.*", timeoutSeconds);
    }

    public String execute(String cmd, String resultPattern, int timeoutSeconds) throws Exception {
        outputStream.write((cmd + "\n").getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < timeoutSeconds * 2; i++) {
            String content = IoUtil.read2StringCharset(inputStream, StandardCharsets.UTF_8);
            result.append(content);
            if (result.toString().matches(resultPattern)) {
                break;
            } else {
                CommonUtil.sleep(TimeUnit.SECONDS, 1);
            }
        }
        return result.toString();
    }

    public void close() {
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
    }

}
