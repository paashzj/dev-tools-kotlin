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

public class PulsarConfig extends BaseDeployConfig {

    private String brokerHttpHost;

    private int brokerHttpPort;

    private String functionHttpHost;

    private int functionHttpPort;

    public PulsarConfig() {
    }

    public PulsarConfig(String name,
                        String k8sName,
                        String namespace,
                        String deployName,
                        String brokerHttpHost,
                        int brokerHttpPort,
                        String functionHttpHost,
                        int functionHttpPort
    ) {
        super(name, k8sName, namespace, deployName);
        this.brokerHttpHost = brokerHttpHost;
        this.brokerHttpPort = brokerHttpPort;
        this.functionHttpHost = functionHttpHost;
        this.functionHttpPort = functionHttpPort;
    }

    public String getBrokerHttpHost() {
        return brokerHttpHost;
    }

    public void setBrokerHttpHost(String brokerHttpHost) {
        this.brokerHttpHost = brokerHttpHost;
    }

    public int getBrokerHttpPort() {
        return brokerHttpPort;
    }

    public void setBrokerHttpPort(int brokerHttpPort) {
        this.brokerHttpPort = brokerHttpPort;
    }

    public String getFunctionHttpHost() {
        return functionHttpHost;
    }

    public void setFunctionHttpHost(String functionHttpHost) {
        this.functionHttpHost = functionHttpHost;
    }

    public int getFunctionHttpPort() {
        return functionHttpPort;
    }

    public void setFunctionHttpPort(int functionHttpPort) {
        this.functionHttpPort = functionHttpPort;
    }
}
