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

package com.github.shoothzj.dev.module;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KfkConfig {

    /**
     * @param url example 127.0.0.1:9092
     * @return
     */
    public static Properties getKfkProducerConfiguration(String url) {
        Properties producerConfig = new Properties();
        producerConfig.put("bootstrap.servers", url);
        producerConfig.put("transaction.timeout.ms", 3000);
        producerConfig.put("acks", "all");
        producerConfig.put("retries", 0);
        producerConfig.put("batch.size", 16384);
        producerConfig.put("key.serializer", StringSerializer.class.getName());
        producerConfig.put("value.serializer", StringSerializer.class.getName());
        return producerConfig;
    }

    /**
     * @param url example 127.0.0.1:9092
     * @return
     */
    public static Properties getKfkConsumerConfiguration(String url) {
        Properties consumerConfig = new Properties();
        consumerConfig.put("bootstrap.servers", url);
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

}
