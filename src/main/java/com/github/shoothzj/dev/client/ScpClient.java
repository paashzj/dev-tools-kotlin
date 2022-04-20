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

package com.github.shoothzj.dev.client;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.scp.client.ScpClientCreator;

public class ScpClient {

    private String host;
    private String username;
    private String password;
    private Integer port;

    ScpClient() {
    }


    public static ScpClient builder() {
        return new ScpClient();
    }

    public ScpClient setHost(String host) {
        this.host = host;
        return this;
    }

    public ScpClient setUsername(String username) {
        this.username = username;
        return this;
    }

    public ScpClient setPassword(String password) {
        this.password = password;
        return this;
    }

    public ScpClient setPort(Integer port) {
        this.port = port;
        return this;
    }

    public Client build() {
        return new Client();
    }

    public class Client {

        public boolean scpFile(String local, String remote) throws Exception {
            // create ssh client
            SshClient client = SshClient.setUpDefaultClient();
            //start ssh client
            client.start();

            // 通过主机IP、端口和用户名，连接主机，获取Session
            ClientSession session = client.connect(username, host, port).verify().getSession();

            // login by password
            session.addPasswordIdentity(password);

            boolean isSuccess = session.auth().verify().isSuccess();
            if (isSuccess) {
                ScpClientCreator creator = ScpClientCreator.instance();
                org.apache.sshd.scp.client.ScpClient scpClient = creator.createScpClient(session);
                scpClient.upload(local, remote, org.apache.sshd.scp.client.ScpClient.Option.Recursive);

                scpClient = null;

                if (session.isOpen()) {
                    session.close();
                }

                if (client.isOpen()) {
                    client.stop();
                    client.close();
                }

            }
            return isSuccess;
        }
    }

}
