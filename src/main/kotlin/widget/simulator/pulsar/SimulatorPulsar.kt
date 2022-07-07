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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.sp
import com.github.shoothzj.dev.simulator.pulsar.PulsarConfigStorage
import module.MqPatternEnum
import widget.component.RowPaddingButton

val config = mutableStateOf(PulsarConfigStorage.getClientConfig())
val pattern = mutableStateOf(MqPatternEnum.Producer)
val allowTlsInsecure = mutableStateOf(config.value.isAllowTlsInsecure)
val tlsSwitch = mutableStateOf(false)
val tlsHostNameVerificationEnable = mutableStateOf(config.value.isEnableTlsHostNameVerification)
val authType = mutableStateOf(config.value.authType)
val keyStorePath = mutableStateOf(config.value.keyStorePath)
val keyStorePassword = mutableStateOf(config.value.keyStorePassword)
val trustStorePath = mutableStateOf(config.value.trustStorePath)
val trustStorePassword = mutableStateOf(config.value.trustStorePassword)
val jwtToken = mutableStateOf(config.value.jwtToken)
var topic = mutableStateOf(config.value.topic)
var pulsarUrl = mutableStateOf(config.value.pulsarUrl)

@Composable
fun SimulatorPulsar() {
    Column {
        Head(pattern)
        when (pattern.value) {
            MqPatternEnum.Producer -> {
                PulsarProducer()
            }
            MqPatternEnum.Consumer -> {
                PulsarConsumer()
            }
        }
    }
}

@Composable
fun Head(pattern: MutableState<MqPatternEnum>) {
    Row {
        Text("MqPattern List", fontSize = 30.sp)
        RowPaddingButton(
            onClick = {
                pattern.value = MqPatternEnum.Producer
            },
        ) {
            Text(text = "producer", fontSize = 12.sp)
        }
        RowPaddingButton(
            onClick = {
                pattern.value = MqPatternEnum.Consumer
            },
        ) {
            Text(text = "consumer", fontSize = 12.sp)
        }
    }
}
