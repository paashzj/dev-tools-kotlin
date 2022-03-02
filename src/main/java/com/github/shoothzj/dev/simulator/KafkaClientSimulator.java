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

package com.github.shoothzj.dev.simulator;

import com.github.shoothzj.dev.kafka.KafkaConfFactory;
import com.github.shoothzj.dev.util.ValidateUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class KafkaClientSimulator {

    private static final Logger log = LoggerFactory.getLogger(KafkaClientSimulator.class);

    public String produce(String host, String port, String saslMechanism, String username, String password, String topic, String key, String msg) {
        if (ValidateUtil.isNotHost(host)) {
            return String.format("host [%s] is illegal", host);
        }
        if (ValidateUtil.isNotPort(port)) {
            return String.format("port [%s] is illegal", port);
        }
        try {
            KafkaProducer<String, String> producer =
                    new KafkaProducer<>(KafkaConfFactory.acquireProducerConf(host, port, saslMechanism, username, password));
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, msg);
            producer.send(record);
            return String.format("send message to kafka success. topic [%s], key [%s]",
                    topic, key);
        } catch (Exception e) {
            log.error("kafka produce exception is ", e);
            return String.format("send message to kafka fail. topic [%s], key [%s], reason [%s]",
                    topic, key, e.getMessage());
        }
    }

    public String consume(String host, String port, String saslMechanism, String username, String password, String topic) {
        if (ValidateUtil.isNotHost(host)) {
            return String.format("host [%s] is illegal", host);
        }
        if (ValidateUtil.isNotPort(port)) {
            return String.format("port [%s] is illegal", port);
        }
        try {
            KafkaConsumer<String, String> consumer =
                    new KafkaConsumer<>(KafkaConfFactory.acquireConsumerConf(host, port, saslMechanism, username, password));
            consumer.subscribe(List.of(topic));
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1000));
            StringBuilder msg = new StringBuilder();
            if (records != null && records.count() > 0) {
                for (ConsumerRecord<String, String> record : records) {
                    log.info("topic {} record value is {}", topic, record.value());
                    msg.append(record.value()).append(System.lineSeparator());
                }
            }
            return msg.substring(0, msg.length() - 1);
        } catch (Exception e) {
            log.error("kafka subscribe exception is ", e);
            return e.getMessage();
        }
    }
}
