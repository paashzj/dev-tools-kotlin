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

import com.github.shoothzj.dev.constant.SystemConst;
import com.github.shoothzj.javatool.util.SocketUtil;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.shell.InteractiveProcessShellFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

public class SshClientTest {

    private static final Logger log = LoggerFactory.getLogger(SshClientTest.class);

    @Ignore
    @Test
    public void testSimpleExecuteCmd() throws Exception {
        SshServer sshServer = SshServer.setUpDefaultServer();
        sshServer.setHost("localhost");
        int freePort = SocketUtil.getFreePort();
        log.info("started ssh server on localhost port {}", freePort);
        sshServer.setPort(freePort);
        sshServer.setKeyPairProvider(new SimpleGeneratorHostKeyProvider());
        sshServer.setPasswordAuthenticator((username, password, session) -> true);
        sshServer.setShellFactory(new InteractiveProcessShellFactory());
        sshServer.start();
        SshClient sshClient = new SshClient("localhost", freePort, SystemConst.USERNAME, "");
        Assert.assertEquals(sshClient.executeOneLineReturn("whoami", 3), SystemConst.USERNAME);
        sshServer.stop();
    }

}
