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

import com.github.shoothzj.dev.constant.LinuxCmdConst;
import com.github.shoothzj.dev.module.shell.FreeMemoryResult;
import com.github.shoothzj.dev.shell.FreeMemoryResultParser;
import com.github.shoothzj.javatool.util.CommonUtil;
import com.github.shoothzj.javatool.util.IoUtil;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class SshClient {

    private final JSch jSch;

    private Session session;

    private ChannelShell channel;

    private InputStream inputStream;

    private OutputStream outputStream;

    public SshClient(String host, int port, String username, String password) throws Exception {
        this.jSch = new JSch();
        login(host, port, username, password);
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

    public FreeMemoryResult freeMemory() throws Exception {
        List<String> result = this.execute(LinuxCmdConst.FREE_MEMORY, 15);
        return FreeMemoryResultParser.parse(result);
    }

    public List<String> execute(String cmd, int timeoutSeconds) throws Exception {
        return execute(cmd, "\\.*", timeoutSeconds);
    }

    /**
     * the output format is
     * ```
     *     [root@iZuf6e1qguryhi8gxdr2fqZ ~]# free -m
     *                   total        used        free      shared  buff/cache   available
     *     Mem:            759         160         215           1         383         464
     *     Swap:             0           0           0
     *     [root@iZuf6e1qguryhi8gxdr2fqZ ~]#
     * ```
     * we need to delete the first and last line
     * @param cmd
     * @param resultPattern
     * @param timeoutSeconds
     * @return
     * @throws Exception
     */
    public List<String> execute(String cmd, String resultPattern, int timeoutSeconds) throws Exception {
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
        List<String> strings = Arrays.asList(result.toString().split("\\n"));
        ArrayList<String> res = new ArrayList<>();
        if (strings.size() <= 2) {
            return res;
        }
        return res.subList(1, strings.size() - 1);
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
