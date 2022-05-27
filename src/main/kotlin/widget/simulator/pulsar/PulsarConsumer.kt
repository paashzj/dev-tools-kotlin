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

package widget.simulator.pulsar

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
import com.github.shoothzj.dev.simulator.pulsar.PulsarClientSimulator
import com.github.shoothzj.dev.simulator.pulsar.PulsarConsumerSimulator
import constant.PulsarConst
import widget.component.RowPaddingButton
import widget.config.ConfigGroupPulsarJwt
import widget.config.ConfigGroupPulsarTls

@Composable
fun PulsarConsumer() {
    var topic by remember { mutableStateOf(PulsarConst.defaultTopic) }
    var pulsarUrl by remember { mutableStateOf(PulsarConst.defaultUrl) }
    var msg by remember { mutableStateOf("") }
    var errMsg by remember { mutableStateOf("") }
    var simulator: PulsarConsumerSimulator? by remember { mutableStateOf(null) }

    Column {
        OutlinedTextField(
            value = pulsarUrl,
            onValueChange = {
                pulsarUrl = it
            },
            label = { Text("pulsar url") }
        )
        OutlinedTextField(
            value = authType.value,
            onValueChange = {
                authType.value = it
            },
            label = { Text("pulsar auth type") }
        )
        OutlinedTextField(
            value = tlsSwitch.value,
            onValueChange = {
                tlsSwitch.value = it
            },
            label = { Text("tlsSwitch") }
        )
        if (authType.value == PulsarConst.authTypeJwt) {
            ConfigGroupPulsarJwt(
                trustStorePath,
                trustStorePassword,
                jwtToken,
            )
        } else if (tlsSwitch.value == "ON") {
            ConfigGroupPulsarTls(
                keyStoreType,
                keyStorePath,
                keyStorePassword,
                trustStorePath,
                trustStorePassword,
            )
        }
        OutlinedTextField(
            value = topic,
            onValueChange = {
                topic = it
            },
            label = { Text("pulsar topic") }
        )
        Row {
            RowPaddingButton(
                onClick = {
                    try {
                        val client = PulsarClientSimulator(
                            pulsarUrl,
                            authType.value,
                            tlsSwitch.value,
                            keyStoreType.value,
                            keyStorePath.value,
                            keyStorePassword.value,
                            trustStorePath.value,
                            trustStorePassword.value,
                            jwtToken.value,
                        )
                        simulator = PulsarConsumerSimulator(client)
                    } catch (e: Exception) {
                        errMsg = e.message.toString()
                    }
                }
            ) { Text(text = R.strings.connect, fontSize = 12.sp) }
            RowPaddingButton(
                onClick = {
                    msg = simulator?.subscribe(topic) ?: "please create pulsar consumer"
                },
            ) {
                Text(text = R.strings.subscribe, fontSize = 12.sp)
            }
            RowPaddingButton(
                onClick = {
                    msg = simulator?.receive() ?: "consume pulsar msg failed"
                },
            ) {
                Text(text = R.strings.receive, fontSize = 12.sp)
            }
            RowPaddingButton(
                onClick = {
                    msg = (simulator?.close() ?: "")
                },
            ) {
                Text(text = R.strings.close, fontSize = 12.sp)
            }
        }
        Text(msg)
        Text(errMsg)
    }
}
