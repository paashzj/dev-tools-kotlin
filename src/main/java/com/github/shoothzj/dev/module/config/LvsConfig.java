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

public class LvsConfig extends BaseConfig {

    private String jumpHost;

    private int jumpPort;

    private String jumpUsername;

    private String jumpPassword;

    private String masterHost;

    private String slaveHost;

    public LvsConfig() {
    }

    public LvsConfig(String name, String jumpHost, int jumpPort,
                     String jumpUsername,
                     String jumpPassword,
                     String masterHost,
                     String slaveHost) {
        super(name);
        this.jumpUsername = jumpUsername;
        this.jumpHost = jumpHost;
        this.jumpPort = jumpPort;
        this.jumpPassword = jumpPassword;
        this.masterHost = masterHost;
        this.slaveHost = slaveHost;
    }

    public String getJumpHost() {
        return jumpHost;
    }

    public void setJumpHost(String jumpHost) {
        this.jumpHost = jumpHost;
    }

    public int getJumpPort() {
        return jumpPort;
    }

    public void setJumpPort(int jumpPort) {
        this.jumpPort = jumpPort;
    }

    public String getJumpUsername() {
        return jumpUsername;
    }

    public void setJumpUsername(String jumpUsername) {
        this.jumpUsername = jumpUsername;
    }

    public String getJumpPassword() {
        return jumpPassword;
    }

    public void setJumpPassword(String jumpPassword) {
        this.jumpPassword = jumpPassword;
    }

    public String getMasterHost() {
        return masterHost;
    }

    public void setMasterHost(String masterHost) {
        this.masterHost = masterHost;
    }

    public String getSlaveHost() {
        return slaveHost;
    }

    public void setSlaveHost(String slaveHost) {
        this.slaveHost = slaveHost;
    }
}
