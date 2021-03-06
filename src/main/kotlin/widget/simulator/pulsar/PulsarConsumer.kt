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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.shoothzj.dev.simulator.pulsar.PulsarClientSimulator
import com.github.shoothzj.dev.simulator.pulsar.PulsarConfigStorage
import com.github.shoothzj.dev.simulator.pulsar.PulsarConsumerSimulator
import com.github.shoothzj.dev.storage.StorageUtil
import constant.PulsarConst
import module.LimitedList
import widget.component.CheckboxInput
import widget.component.DropdownList
import widget.component.RowPaddingButton
import widget.component.TextLogger
import widget.config.ConfigGroupPulsarAuthJwt
import widget.config.ConfigGroupPulsarAuthTls
import widget.config.ConfigGroupPulsarTls
import kotlin.concurrent.thread

val pulsarMsgListUi: MutableState<List<String>> = mutableStateOf(listOf())
val pulsarMsgList = LimitedList(0, 500)

@Composable
fun PulsarConsumer() {
    var msg by remember { mutableStateOf("") }
    var errMsg by remember { mutableStateOf("") }
    var simulator: PulsarConsumerSimulator? by remember { mutableStateOf(null) }

    var isConnect by remember { mutableStateOf(PulsarConst.closed) }
    var isOpenManual by remember { mutableStateOf(true) }
    var isOpenAuto by remember { mutableStateOf(true) }
    val allowSaveMsg = mutableStateOf(false)
    var position = mutableStateOf(PulsarConst.Latest)

    Row {
        Column(modifier = Modifier.verticalScroll(rememberScrollState()).weight(1f)) {
            OutlinedTextField(
                value = pulsarUrl.value,
                onValueChange = {
                    pulsarUrl.value = it
                },
                label = { Text("pulsar url") }
            )
            CheckboxInput("allow tls Insecure", allowTlsInsecure)
            CheckboxInput("tls enable", tlsSwitch)
            CheckboxInput("allow save msg to filex", allowSaveMsg)
            SelectionContainer {
                Text("file path: " + StorageUtil.SIMULATOR_PULSAR_MSG_STORAGE_PATH)
            }
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
            DropdownList(PulsarConst.positionList, "consume position", position)
            OutlinedTextField(
                value = topic.value,
                onValueChange = {
                    topic.value = it
                },
                label = { Text("pulsar topic") }
            )
            Row {
                RowPaddingButton(
                    onClick = {
                        try {
                            if (isConnect == PulsarConst.closed) {
                                val client = PulsarClientSimulator(
                                    pulsarUrl.value,
                                    tlsSwitch.value,
                                    allowTlsInsecure.value,
                                    tlsHostNameVerificationEnable.value,
                                    authType.value,
                                    keyStorePath.value,
                                    keyStorePassword.value,
                                    trustStorePath.value,
                                    trustStorePassword.value,
                                    jwtToken.value,
                                    topic.value,
                                    allowSaveMsg.value,
                                    position.value,
                                )
                                PulsarConfigStorage.saveClientConfig(client)
                                simulator = PulsarConsumerSimulator(client)
                                msg = simulator?.subscribe(topic.value, position.value) ?: "please create pulsar consumer"
                                if (!msg.contains("exception")) {
                                    isConnect = PulsarConst.connected
                                }
                                clearMsg()
                            } else {
                                msg = "consumer is already subscribe, please close consumer and retry connect."
                            }
                        } catch (e: Exception) {
                            errMsg = e.message.toString()
                        }
                    },
                ) {
                    Text(text = R.strings.subscribe, fontSize = 12.sp)
                }
                RowPaddingButton(
                    onClick = {
                        if (isConnect == PulsarConst.connected) {
                            msg = (simulator?.close() ?: "")
                            isConnect = PulsarConst.closed
                        } else {
                            msg = "consumer is already closed."
                        }
                        isOpenAuto = true
                        isOpenManual = true
                    },
                ) {
                    Text(text = R.strings.close, fontSize = 12.sp)
                }

                Column(modifier = Modifier.padding(all = 23.dp)) {
                    Text("status :$isConnect", fontSize = 18.sp)
                }
            }

            Row {
                // manual
                Text(R.strings.ManualConsume, fontSize = 40.sp)
                Row {
                    RowPaddingButton(
                        onClick = {
                            if (simulator == null || isConnect != PulsarConst.connected) {
                                msg = "pulsar consumer is not subscribe."
                            } else {
                                isOpenAuto = false
                                isOpenManual = true
                                val receiveResp = simulator!!.receive(allowSaveMsg.value)
                                if (receiveResp.isSuccess) {
                                    addPulsarMsg(receiveResp.t)
                                    receiveResp.t
                                } else {
                                    receiveResp.msg
                                }
                            }
                        },
                        isOpenManual,
                    ) {
                        Text(text = R.strings.receive, fontSize = 12.sp)
                    }
                }
            }
            Row {
                Text(R.strings.AutoConsume, fontSize = 40.sp)
                Row {
                    RowPaddingButton(
                        onClick = {
                            if (isConnect == PulsarConst.connected) {
                                isOpenManual = false
                                msg = ""
                                thread(start = true) {
                                    while (true) {
                                        val receiveResp = simulator?.receive(allowSaveMsg.value)
                                        if (receiveResp != null) {
                                            if (receiveResp.isSuccess) {
                                                addPulsarMsg(receiveResp.t)
                                            }
                                        }
                                    }
                                }
                            }
                        },
                        isOpenAuto,
                    ) {
                        Text(text = R.strings.AutoConsume, fontSize = 12.sp)
                    }
                }
            }
            Text(msg)
            Text(errMsg)
        }
        Column(modifier = Modifier.weight(2f)) {
            Text(R.strings.msgList)
            TextLogger(pulsarMsgListUi)
        }
    }
}

fun addPulsarMsg(msg: String) {
    pulsarMsgList.add(msg)
    pulsarMsgListUi.value = pulsarMsgList.getVal()
}

fun clearMsg() {
    pulsarMsgList.clear()
    pulsarMsgListUi.value = pulsarMsgList.getVal()
}
