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
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.SubscriptionInitialPosition;
import org.apache.pulsar.client.api.SubscriptionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PulsarConsumerSimulator {

    private static final Logger log = LoggerFactory.getLogger(PulsarConsumerSimulator.class);

    private final PulsarClientSimulator pulsarClientSimulator;

    private static final Integer MAX_RECEIVE_MSG = 100;

    private Consumer<byte[]> consumer;

    public PulsarConsumerSimulator(PulsarClientSimulator pulsarClientSimulator) {
        this.pulsarClientSimulator = pulsarClientSimulator;
    }

    public String subscribe(String topic) {
        try {
            PulsarClient pulsarClient = pulsarClientSimulator.getPulsarClient();
            consumer = pulsarClient.newConsumer().topic(topic).subscriptionName(UUID.randomUUID().toString())
                    .receiverQueueSize(MAX_RECEIVE_MSG).autoUpdatePartitions(true).subscriptionType(SubscriptionType.Failover)
                    .subscriptionInitialPosition(SubscriptionInitialPosition.Latest)
                    .subscribe();
            return "pulsar subscribe success";
        } catch (PulsarClientException e) {
            return String.format("pulsar subscribe exception : %s", e.getMessage());
        }
    }

    public String receive() {
        try {
            Message<byte[]> receive = consumer.receive(100, TimeUnit.MILLISECONDS);
            if (receive == null) {
                return "no msg available";
            }
            return new String(receive.getValue());
        } catch (Exception e) {
            log.warn("consume msg failed. e : {}", ExceptionUtil.getException(e));
        }
        return "consume msg failed.";
    }

    public String close() {
        try {
            consumer.close();
        } catch (PulsarClientException e) {
            log.error("close consumer failed. e : {}", ExceptionUtil.getException(e));
        }
        try {
            pulsarClientSimulator.close();
        } catch (Exception e) {
            log.error("close pulsar client failed. e: {}", ExceptionUtil.getException(e));
        }
        return "success";
    }
}
