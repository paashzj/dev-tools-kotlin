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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.github.shoothzj.dev.simulator.pulsar.PulsarClientSimulator
import com.github.shoothzj.dev.simulator.pulsar.PulsarProducerSimulator
import constant.PulsarConst
import widget.component.DropdownBool
import widget.component.DropdownList
import widget.component.RowPaddingButton
import widget.config.ConfigGroupPulsarAuthJwt
import widget.config.ConfigGroupPulsarAuthTls
import widget.config.ConfigGroupPulsarTls

@Composable
fun PulsarProducer() {
    var topic by remember { mutableStateOf(PulsarConst.defaultTopic) }
    var pulsarUrl by remember { mutableStateOf(PulsarConst.defaultUrl) }
    var msg by remember { mutableStateOf("") }
    var key by remember { mutableStateOf("") }
    var res by remember { mutableStateOf("") }
    var simulator: PulsarProducerSimulator? by remember { mutableStateOf(null) }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        OutlinedTextField(
            value = pulsarUrl,
            onValueChange = {
                pulsarUrl = it
            },
            label = { Text("pulsar url") }
        )
        DropdownBool("tls enable", tlsSwitch)
        if (tlsSwitch.value) {
            ConfigGroupPulsarTls(
                tlsHostNameVerificationEnable
            )
        }
        DropdownList(PulsarConst.authTypeList, "pulsar auth type", authType)
        if (authType.value == PulsarConst.authTypeJwt) {
            ConfigGroupPulsarAuthJwt(
                trustStorePath,
                trustStorePassword,
                jwtToken,
            )
        } else if (authType.value == PulsarConst.authTypeTls) {
            ConfigGroupPulsarAuthTls(
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
        OutlinedTextField(
            value = key,
            onValueChange = {
                key = it
            },
            label = { Text("pulsar key") }
        )
        OutlinedTextField(
            value = msg,
            onValueChange = {
                msg = it
            },
            label = { Text("pulsar message") }
        )
        Row {
            RowPaddingButton(
                onClick = {
                    try {
                        val client = PulsarClientSimulator(
                            pulsarUrl,
                            tlsSwitch.value,
                            tlsHostNameVerificationEnable.value,
                            authType.value,
                            keyStorePath.value,
                            keyStorePassword.value,
                            trustStorePath.value,
                            trustStorePassword.value,
                            jwtToken.value,
                        )
                        simulator = PulsarProducerSimulator(topic, client)
                    } catch (e: Exception) {
                        res = e.message.toString()
                    }
                }
            ) { Text(text = R.strings.connect, fontSize = 12.sp) }

            RowPaddingButton(
                onClick = {
                    res = simulator?.produce(msg, key) ?: "please create pulsar producer"
                },
            ) {
                Text(text = R.strings.send, fontSize = 12.sp)
            }
            RowPaddingButton(
                onClick = {
                    res = (simulator?.close() ?: "")
                },
            ) {
                Text(text = R.strings.close, fontSize = 12.sp)
            }
        }
        Text(res)
    }
}
