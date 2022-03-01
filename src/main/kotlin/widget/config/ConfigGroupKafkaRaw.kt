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

package widget.config

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import org.apache.kafka.common.security.auth.SecurityProtocol
import widget.component.DropdownList

@Composable
fun ConfigGroupKafkaRaw(
    editKafkaHost: MutableState<String>,
    editKafkaPort: MutableState<String>,
    editKafkaSaslMechanism: MutableState<String>,
    editKafkaUsername: MutableState<String>,
    editKafkaPassword: MutableState<String>,
) {
    Column {
        ConfigItemHost(editKafkaHost, "kafka host", mutableStateOf(""))
        ConfigItemPort(editKafkaPort, "kafka port", mutableStateOf(""))
        DropdownList(arrayListOf("", SecurityProtocol.SASL_PLAINTEXT.name), "kafka sasl mechanism", editKafkaSaslMechanism)
        ConfigItemString(editKafkaUsername, "kafka username")
        ConfigItemString(editKafkaPassword, "kafka password")
    }
}
