/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache license, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */

package widget.simulator.kafka

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import com.github.shoothzj.dev.simulator.kafka.KafkaConfigStorage
import com.github.shoothzj.dev.simulator.kafka.KafkaConsumerSimulator
import widget.component.CheckboxInput
import widget.component.RowPaddingButton
import widget.config.ConfigGroupKafkaRaw
import widget.config.ConfigGroupKafkaTls

@Composable
fun KafkaConsumer() {
    var msg by remember { mutableStateOf("") }
    var consumer: KafkaConsumerSimulator? by remember { mutableStateOf(null) }

    Column {
        ConfigGroupKafkaRaw(
            host,
            port,
            saslMechanism,
            username,
            password,
        )
        OutlinedTextField(
            value = topic.value,
            onValueChange = {
                topic.value = it
            },
            label = { Text("kafka topic") }
        )
        CheckboxInput("tls enable", enableTls)
        if (enableTls.value) {
            ConfigGroupKafkaTls(
                keyStorePath,
                keyStorePassword,
            )
        }
        Row {
            RowPaddingButton(
                onClick = {
                    try {
                        consumer = KafkaConsumerSimulator(
                            topic.value,
                            host.value,
                            port.value,
                            saslMechanism.value,
                            username.value,
                            password.value,
                            enableTls.value,
                            keyStorePath.value,
                            keyStorePassword.value,
                        )
                        KafkaConfigStorage.saveClientConfig(consumer)
                    } catch (e: Exception) {
                        msg = e.message.toString()
                    }
                }
            ) { Text(text = R.strings.connect, fontSize = 12.sp) }
            RowPaddingButton(
                onClick = {
                    msg = consumer?.subscribe(topic.value) ?: "please create kafka consumer"
                },
            ) {
                Text(text = R.strings.subscribe, fontSize = 12.sp)
            }
            RowPaddingButton(
                onClick = {
                    msg = consumer?.receive(topic.value) ?: "please create kafka consumer"
                },
            ) {
                Text(text = R.strings.receive, fontSize = 12.sp)
            }
            RowPaddingButton(
                onClick = {
                    msg = consumer?.close() ?: "close consumer failed."
                },
            ) {
                Text(text = R.strings.close, fontSize = 12.sp)
            }
        }
        Text(msg)
    }
}
