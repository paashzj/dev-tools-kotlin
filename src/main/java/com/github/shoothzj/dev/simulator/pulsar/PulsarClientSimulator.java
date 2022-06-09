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

import com.github.shoothzj.dev.constant.PulsarConst;
import com.github.shoothzj.javatool.util.ExceptionUtil;
import com.github.shoothzj.javatool.util.StreamUtil;
import com.google.common.collect.Sets;
import org.apache.pulsar.client.api.AuthenticationFactory;
import org.apache.pulsar.client.api.ClientBuilder;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.impl.auth.AuthenticationKeyStoreTls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PulsarClientSimulator {

    private static final Logger log = LoggerFactory.getLogger(PulsarClientSimulator.class);

    private static final Set<String> protocols = Sets.newHashSet("TLSv1.2");
    private static final Set<String> cipher = Sets.newHashSet(
            "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256",
            "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384",
            "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256",
            "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384"
    );

    private static PulsarClient pulsarClient;

    private final String pulsarUrl;

    private final boolean enableTls;

    private final boolean allowTlsInsecure;

    private final boolean enableTlsHostNameVerification;

    private final String authType;

    private final String keyStorePath;

    private final String keyStorePassword;

    private final String trustStorePath;

    private final String trustStorePassword;

    private final String jwtToken;

    public PulsarClientSimulator(String pulsarUrl, boolean enableTls, boolean allowTlsInsecure, boolean enableTlsHostNameVerification, String authType, String keyStorePath, String keyStorePassword, String trustStorePath, String trustStorePassword, String jwtToken) {
        this.pulsarUrl = pulsarUrl;
        this.enableTls = enableTls;
        this.allowTlsInsecure = allowTlsInsecure;
        this.enableTlsHostNameVerification = enableTlsHostNameVerification;
        this.authType = authType;
        this.keyStorePath = keyStorePath;
        this.keyStorePassword = keyStorePassword;
        this.trustStorePath = trustStorePath;
        this.trustStorePassword = trustStorePassword;
        this.jwtToken = jwtToken;
    }

    public PulsarClient getPulsarClient() {
        if (pulsarClient != null) {
            return pulsarClient;
        }
        try {
            ClientBuilder clientBuilder = PulsarClient.builder();
            clientBuilder.serviceUrl(pulsarUrl);
            if (enableTls) {
                clientBuilder.allowTlsInsecureConnection(allowTlsInsecure);
                clientBuilder.enableTlsHostnameVerification(enableTlsHostNameVerification);
            }
            switch (authType) {
                case PulsarConst.AUTH_TYPE_NONE -> {
                    pulsarClient = clientBuilder.build();
                    return pulsarClient;
                }
                case PulsarConst.AUTH_TYPE_JWT -> {
                    pulsarClient = clientBuilder
                            .tlsProtocols(protocols).tlsCiphers(cipher)
                            .useKeyStoreTls(true)
                            .tlsTrustStorePath(trustStorePath)
                            .tlsTrustStorePassword(trustStorePassword)
                            .authentication(AuthenticationFactory.token(jwtToken)).build();
                    return pulsarClient;
                }
                case PulsarConst.AUTH_TYPE_TLS -> {
                    Map<String, String> map = new HashMap<>();
                    map.put(AuthenticationKeyStoreTls.KEYSTORE_TYPE, PulsarConst.DEFAULT_CERT_TYPE);
                    map.put(AuthenticationKeyStoreTls.KEYSTORE_PATH, keyStorePath);
                    map.put(AuthenticationKeyStoreTls.KEYSTORE_PW, keyStorePassword);
                    pulsarClient = clientBuilder
                            .tlsProtocols(protocols).tlsCiphers(cipher)
                            .useKeyStoreTls(true)
                            .tlsTrustStorePath(trustStorePath)
                            .tlsTrustStorePassword(trustStorePassword)
                            .authentication("org.apache.pulsar.client.impl.auth.AuthenticationKeyStoreTls", map).build();
                    return pulsarClient;
                }
            }
        } catch (Exception e) {
            log.error("create pulsar client failed. e: {}", ExceptionUtil.getException(e));
        }
        return null;
    }

    public void close() {
        closeClientIfNotNull(pulsarClient);
        pulsarClient = null;
    }

    private void closeClientIfNotNull(PulsarClient client) {
        if (client != null) {
            StreamUtil.close(client);
        }
    }

}
