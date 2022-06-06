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
import module.MqPatternEnum
import widget.component.RowPaddingButton

val pattern = mutableStateOf(MqPatternEnum.Producer)

val allowTlsInsecure = mutableStateOf(false)
val tlsSwitch = mutableStateOf(false)
val tlsHostNameVerificationEnable = mutableStateOf(true)
val authType = mutableStateOf("NONE")
val keyStorePath = mutableStateOf("")
val keyStorePassword = mutableStateOf("")
val trustStorePath = mutableStateOf("")
val trustStorePassword = mutableStateOf("")
val jwtToken = mutableStateOf("")

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
