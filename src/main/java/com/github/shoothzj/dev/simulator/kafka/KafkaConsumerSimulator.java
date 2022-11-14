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

import com.github.shoothzj.dev.kafka.KafkaConfFactory;
import com.github.shoothzj.dev.util.ValidateUtil;
import com.google.common.base.Preconditions;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class KafkaConsumerSimulator extends KafkaConfigSimulator {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerSimulator.class);

    private final Consumer<String, String> consumer;

    public KafkaConsumerSimulator(String topic, String host, String port) {
        this(topic, host, port, "", "", "", false, "", "");
    }

    public KafkaConsumerSimulator(String topic, String host, String port, String saslMechanism, String username, String password, boolean enableTls, String trustStorePath, String trustStorePwd) {
        super(host, port, username, password, enableTls, trustStorePath, trustStorePwd, topic, saslMechanism);
        Preconditions.checkArgument(ValidateUtil.isHost(host), String.format("host [%s] is illegal", host));
        Preconditions.checkArgument(ValidateUtil.isPort(port), String.format("port [%s] is illegal", port));
        Properties consumerConf = KafkaConfFactory.acquireConsumerConf(host, port, saslMechanism, username, password, enableTls, trustStorePath, trustStorePwd);
        consumer = new KafkaConsumer<>(consumerConf);
    }

    public String subscribe(String topic) {
        try {
            consumer.subscribe(List.of(topic));
            return "kafka subscribe success";
        } catch (Exception e) {
            log.error("kafka subscribe exception ", e);
            return String.format("kafka subscribe exception : %s", e.getMessage());
        }
    }

    public String receive(String topic) {
        try {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            if (records == null || records.count() == 0) {
                return "receive empty list of message";
            }
            List<String> msgList = new ArrayList<>();
            for (ConsumerRecord<String, String> record : records) {
                log.info("topic {} offset {} record value is {}", topic, record.offset(), record.value());
                msgList.add(String.format("offset: %d", record.offset()) + System.lineSeparator() + record.value());
            }
            return msgList.stream().collect(Collectors.joining(System.lineSeparator()));
        } catch (Exception e) {
            log.error("kafka receive exception ", e);
            return String.format("kafka receive exception : %s", e.getMessage());
        }
    }

    public String close() {
        consumer.close();
        return "success close consumer";
    }
}
