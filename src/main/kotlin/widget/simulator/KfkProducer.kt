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

package widget.simulator

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.github.shoothzj.dev.SimulatorKafka
import widget.config.ConfigItemHost
import widget.config.ConfigItemPort

@Composable
fun KafkaProducer() {
    val host = mutableStateOf("localhost")
    val port = mutableStateOf("9092")
    var topic by remember { mutableStateOf("") }
    var msg by remember { mutableStateOf("") }
    var key by remember { mutableStateOf("") }
    var res by remember { mutableStateOf("") }

    Column {
        ConfigItemHost(host, "kafka host", mutableStateOf(""))
        ConfigItemPort(port, "kafka port", mutableStateOf(""))
        OutlinedTextField(
            value = topic,
            onValueChange = {
                topic = it
            },
            label = { Text("kafka topic") }
        )
        OutlinedTextField(
            value = key,
            onValueChange = {
                key = it
            },
            label = { Text("kafka key") }
        )
        OutlinedTextField(
            value = msg,
            onValueChange = {
                msg = it
            },
            label = { Text("kafka message") }
        )

        Button(onClick = {
            val client = SimulatorKafka()
            res = client.producer(host.value, port.value, topic, key, msg)
        }) { Text(R.strings.send) }
        Text(res)
    }
}
