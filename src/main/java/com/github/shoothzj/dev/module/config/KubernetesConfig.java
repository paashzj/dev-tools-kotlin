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

package com.github.shoothzj.dev.module.config;

public class KubernetesConfig extends BaseConfig {

    private String host;

    private int port;

    private SshStep sshStep;

    public KubernetesConfig() {
    }

    public KubernetesConfig(String name, String host, int port, String username, String password) {
        super(name);
        this.host = host;
        this.port = port;
        this.sshStep = new SshStep();
        this.sshStep.setUsername(username);
        this.sshStep.setPassword(password);
    }

    public KubernetesConfig(String name, String host, int port, String username, String password, String rootPassword) {
        super(name);
        this.host = host;
        this.port = port;
        this.sshStep = new SshStep();
        this.sshStep.setUsername(username);
        this.sshStep.setPassword(password);
        this.sshStep.setSuUsername("root");
        this.sshStep.setSuPassword(rootPassword);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public SshStep getSshStep() {
        return sshStep;
    }

    public void setSshStep(SshStep sshStep) {
        this.sshStep = sshStep;
    }
}
