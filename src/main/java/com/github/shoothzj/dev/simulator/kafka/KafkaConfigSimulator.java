/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.github.shoothzj.dev.simulator.kafka;

import java.util.Objects;

public class KafkaConfigSimulator {

    private String host;

    private String port;

    private String username;

    private String password;

    private boolean enableTls;

    private String saslMechanism;

    private String trustStorePath;

    private String trustStorePassword;

    private String topic;

    public KafkaConfigSimulator() {
    }

    public KafkaConfigSimulator(String host, String port, String username, String password, boolean enableTls, String trustStorePath, String trustStorePassword, String topic, String saslMechanism) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.enableTls = enableTls;
        this.trustStorePath = trustStorePath;
        this.trustStorePassword = trustStorePassword;
        this.topic = topic;
        this.saslMechanism = saslMechanism;
    }

    public String getHost() {
        return Objects.requireNonNullElse(host, "127.0.0.1");
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return Objects.requireNonNullElse(port, "9092");
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return Objects.requireNonNullElse(username, "");
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return Objects.requireNonNullElse(password, "");
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getEnableTls() {
        return enableTls;
    }

    public void setEnableTls(boolean enableTls) {
        this.enableTls = enableTls;
    }

    public String getTrustStorePath() {
        return Objects.requireNonNullElse(trustStorePath, "");
    }

    public void setTrustStorePath(String trustStorePath) {
        this.trustStorePath = trustStorePath;
    }

    public String getTrustStorePassword() {
        return Objects.requireNonNullElse(trustStorePassword, "");
    }

    public void setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }

    public String getTopic() {
        return Objects.requireNonNullElse(topic, "");
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSaslMechanism() {
        return Objects.requireNonNullElse(saslMechanism, "SASL_PLAINTEXT");
    }

    public void setSaslMechanism(String saslMechanism) {
        this.saslMechanism = saslMechanism;
    }
}
