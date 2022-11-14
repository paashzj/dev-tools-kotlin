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

package com.github.shoothzj.dev.kafka;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.security.auth.SecurityProtocol;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaConfFactory {

    /**
     * @param host example 127.0.0.1
     * @param port example 9092
     * @return
     */
    public static Properties acquireProducerConf(String host, String port, String saslMechanism, String username, String password, boolean enableTls, String trustStorePath, String trustStorePwd) {
        Properties producerConfig = acquireConf(host, port, saslMechanism, username, password, enableTls, trustStorePath, trustStorePwd);
        producerConfig.put("transaction.timeout.ms", 3000);
        producerConfig.put("acks", "all");
        producerConfig.put("retries", 0);
        producerConfig.put("batch.size", 16384);
        producerConfig.put("key.serializer", StringSerializer.class.getName());
        producerConfig.put("value.serializer", StringSerializer.class.getName());
        return producerConfig;
    }

    /**
     * @param host example 127.0.0.1
     * @param port example 9092
     * @return
     */
    public static Properties acquireConsumerConf(String host, String port, String saslMechanism, String username, String password, boolean enableTls, String keyStorePath, String keyStorePwd) {
        Properties consumerConfig = acquireConf(host, port, saslMechanism, username, password, enableTls, keyStorePath, keyStorePwd);
        consumerConfig.put("enable.auto.commit", "true");
        consumerConfig.put("auto.commit.interval.ms", "1000");
        consumerConfig.put("session.timeout.ms", "30000");
        consumerConfig.put("group.id", "groupid");
        consumerConfig.put("max.poll.records", 1000);
        consumerConfig.put("auto.offset.reset", "earliest");
        consumerConfig.put("key.deserializer", StringDeserializer.class.getName());
        consumerConfig.put("value.deserializer", StringDeserializer.class.getName());
        return consumerConfig;
    }

    private static Properties acquireConf(String host, String port, String saslMechanism, String username, String password, boolean enableTls, String trustStorePath, String trustStorePwd) {
        Properties props = new Properties();
        props.put("bootstrap.servers", String.format("%s:%s", host, port));
        if (saslMechanism.equals(SecurityProtocol.SASL_PLAINTEXT.name)) {
            props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SecurityProtocol.SASL_PLAINTEXT.name);
            props.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
            String saslJaasConfig = String.format("""
                    org.apache.kafka.common.security.plain.PlainLoginModule required\s
                    username="%s"\s
                    password="%s";""", username, password);
            props.put(SaslConfigs.SASL_JAAS_CONFIG, saslJaasConfig);
            return props;
        }
        if (enableTls) {
            props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, saslMechanism);
            props.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
            String saslJaasConfig = String.format("""
                    org.apache.kafka.common.security.plain.PlainLoginModule required\s
                    username="%s"\s
                    password="%s";""", username, password);
            props.put(SaslConfigs.SASL_JAAS_CONFIG, saslJaasConfig);
            props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, trustStorePath);
            props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, trustStorePwd);
        }
        return props;
    }

}
