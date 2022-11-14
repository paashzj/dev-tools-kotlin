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

package com.github.shoothzj.dev.simulator.kafka;

import com.github.shoothzj.dev.kafka.KafkaConfFactory;
import com.github.shoothzj.dev.util.ValidateUtil;
import com.google.common.base.Preconditions;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class KafkaProducerSimulator extends KafkaConfigSimulator {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducerSimulator.class);

    private final Producer<String, String> producer;

    public KafkaProducerSimulator(String topic, String host, String port) {
        this(topic, host, port, "", "", "", false, "", "");
    }

    public KafkaProducerSimulator(String topic, String host, String port, String saslMechanism, String username, String password, boolean enableTls, String trustStorePath, String trustStorePwd) {
        super(host, port, username, password, enableTls, trustStorePath, trustStorePwd, topic, saslMechanism);
        Preconditions.checkArgument(ValidateUtil.isHost(host), String.format("host [%s] is illegal", host));
        Preconditions.checkArgument(ValidateUtil.isPort(port), String.format("port [%s] is illegal", port));
        Properties properties = KafkaConfFactory.acquireProducerConf(host, port, saslMechanism, username, password, enableTls, trustStorePath, trustStorePwd);
        producer = new KafkaProducer<>(properties);
    }

    public String send(String topic, String key, String msg) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, msg);
        try {
            RecordMetadata metadata = producer.send(record).get();
            return String.format("send message to kafka success. topic [%s], key [%s], msg [%s]", metadata.topic(), key, msg);
        } catch (Exception e) {
            log.error("kafka produce exception is ", e);
            return String.format("send message to kafka fail. topic [%s], key [%s], reason [%s]", topic, key, e.getMessage());
        }
    }

    public String close() {
        producer.close();
        return "success close producer";
    }
}
