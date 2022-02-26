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

@Composable
fun KafkaConsumer() {

    var host by remember { mutableStateOf("") }
    var port by remember { mutableStateOf("") }
    var topic by remember { mutableStateOf("") }
    var msg by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = host,
            onValueChange = {
                host = it
            },
            label = { Text("kafka host") }
        )
        OutlinedTextField(
            value = port,
            onValueChange = {
                port = it
            },
            label = { Text("kafka port") }
        )
        OutlinedTextField(
            value = topic,
            onValueChange = {
                topic = it
            },
            label = { Text("kafka topic") }
        )
        Button(onClick = {
            var consumer = SimulatorKafka()
            msg = consumer.consumer(host, port, topic)
        }) { Text("receive") }
        Text(msg)
    }
}
