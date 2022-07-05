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

import com.github.shoothzj.dev.module.UiResp;
import com.github.shoothzj.javatool.util.ExceptionUtil;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.SubscriptionInitialPosition;
import org.apache.pulsar.client.api.SubscriptionType;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.PulsarClientException;
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
            if (consumer != null) {
                return "pulsar consumer already subscribe.";
            }
            PulsarClient pulsarClient = pulsarClientSimulator.getPulsarClient();
            consumer = pulsarClient.newConsumer().topic(topic).subscriptionName(UUID.randomUUID().toString())
                    .receiverQueueSize(MAX_RECEIVE_MSG).autoUpdatePartitions(true).subscriptionType(SubscriptionType.Failover)
                    .subscriptionInitialPosition(SubscriptionInitialPosition.Latest)
                    .subscribe();
            return "pulsar subscribe success";
        } catch (Exception e) {
            return String.format("pulsar subscribe exception : %s", e.getMessage());
        }
    }

    public UiResp<String> receive() {
        try {
            Message<byte[]> receive = consumer.receive(100, TimeUnit.MILLISECONDS);
            if (receive == null) {
                return new UiResp<>(false, "", "no msg available");
            }
            return new UiResp<>(true, new String(receive.getValue()), "");
        } catch (Exception e) {
            String errMsg = String.format("consume msg failed. e : %s", ExceptionUtil.getException(e));
            log.error(errMsg);
            return new UiResp<>(false, "", errMsg);
        }
    }

    public String autoReceive(String topic) {
        try {
            PulsarClient pulsarClient = pulsarClientSimulator.getPulsarClient();
            pulsarClient.newConsumer()
                    .topic(topic)
                    .subscriptionName(UUID.randomUUID().toString())
                    .receiverQueueSize(MAX_RECEIVE_MSG)
                    .autoUpdatePartitions(true)
                    .subscriptionType(SubscriptionType.Failover)
                    .subscriptionInitialPosition(SubscriptionInitialPosition.Latest)
                    .messageListener((MessageListener<byte[]>) (consumer, msg) -> {
                                try {
                                    consumer.acknowledge(msg);
                                } catch (PulsarClientException e) {
                                    log.error("ask message fail. messageId[{}]", msg.getMessageId());
                                }
                            }
                    )
                    .subscribe();
            return "auto consume success";
        } catch (Exception e) {
            String errMsg = String.format("auto consume msg failed. e : %s", ExceptionUtil.getException(e));
            log.error(errMsg);
            return errMsg;
        }
    }

    public String close() {
        try {
            consumer.close();
            consumer = null;
        } catch (Exception e) {
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
