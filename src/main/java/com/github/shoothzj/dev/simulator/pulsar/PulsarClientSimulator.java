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

package com.github.shoothzj.dev.simulator.pulsar;

import com.github.shoothzj.javatool.util.ExceptionUtil;
import com.google.common.collect.Sets;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.impl.auth.AuthenticationKeyStoreTls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PulsarClientSimulator {

    private static final Logger log = LoggerFactory.getLogger(PulsarClientSimulator.class);

    private static final String TLS_SWITCH_OFF = "OFF";
    private static final String TLS_SWITCH_ON = "ON";
    private static final Set<String> protocols = Sets.newHashSet("TLSv1.2");
    private static final Set<String> cipher = Sets.newHashSet(
            "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256",
            "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384",
            "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256",
            "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384"
    );

    private static PulsarClient pulsarTlsClient;

    private static PulsarClient pulsarNoTlsClient;

    private final String pulsarUrl;

    private final String tlsSwitch;

    private final String keyStoreType;

    private final String keyStorePath;

    private final String keyStorePassword;

    private final String trustStorePath;

    private final String trustStorePassword;

    public PulsarClientSimulator(String pulsarUrl, String tlsSwitch, String keyStoreType, String keyStorePath, String keyStorePassword, String trustStorePath, String trustStorePassword) {
        this.pulsarUrl = pulsarUrl;
        this.tlsSwitch = tlsSwitch;
        this.keyStoreType = keyStoreType;
        this.keyStorePath = keyStorePath;
        this.keyStorePassword = keyStorePassword;
        this.trustStorePath = trustStorePath;
        this.trustStorePassword = trustStorePassword;
    }

    public PulsarClient getPulsarClient() {
        if (TLS_SWITCH_ON.equals(tlsSwitch) && pulsarTlsClient != null) {
            return pulsarTlsClient;
        }
        if (TLS_SWITCH_OFF.equals(tlsSwitch) && pulsarNoTlsClient != null) {
            return pulsarNoTlsClient;
        }
        try {
            if (TLS_SWITCH_OFF.equals(tlsSwitch)) {
                pulsarNoTlsClient = PulsarClient.builder().serviceUrl(pulsarUrl).build();
                return pulsarNoTlsClient;
            }
            if (TLS_SWITCH_ON.equals(tlsSwitch)) {
                Map<String, String> map = new HashMap<>();
                map.put(AuthenticationKeyStoreTls.KEYSTORE_TYPE, keyStoreType);
                map.put(AuthenticationKeyStoreTls.KEYSTORE_PATH, keyStorePath);
                map.put(AuthenticationKeyStoreTls.KEYSTORE_PW, keyStorePassword);
                pulsarTlsClient = PulsarClient.builder().allowTlsInsecureConnection(false).enableTlsHostnameVerification(true)
                        .tlsProtocols(protocols).tlsCiphers(cipher)
                        .useKeyStoreTls(true)
                        .tlsTrustStorePath(trustStorePath)
                        .tlsTrustStorePassword(trustStorePassword)
                        .authentication("org.apache.pulsar.client.impl.auth.AuthenticationKeyStoreTls", map).build();
                return pulsarTlsClient;
            }
        } catch (PulsarClientException e) {
            log.error("create pulsar client failed. e: {}", ExceptionUtil.getException(e));
        }
        return null;
    }

    public void close() {
        try {
            pulsarTlsClient.close();
            pulsarTlsClient = null;
        } catch (Exception e) {
            log.error("close pulsar tls client failed.");
        }
        try {
            pulsarNoTlsClient.close();
            pulsarNoTlsClient = null;
        } catch (PulsarClientException e) {
            log.error("close pulsar no tls client failed.");
        }
    }
}
