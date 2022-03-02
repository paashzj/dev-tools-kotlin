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

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.github.shoothzj.dev.module.config.PulsarConfig
import com.github.shoothzj.dev.storage.StorageK8s
import com.github.shoothzj.dev.storage.StoragePulsar
import widget.component.DropdownList

@Composable
fun ConfigPulsar() {
    val dialogState = mutableStateOf(false)
    val errorState = mutableStateOf("")
    val pulsarList = mutableStateOf(StoragePulsar.getInstance().listContent())
    val editPulsarName = mutableStateOf("default")
    val editK8sInstanceName = mutableStateOf("")
    val editPulsarNamespace = mutableStateOf("default")
    val editPulsarDeployName = mutableStateOf("pulsar")
    val kubernetes = StorageK8s.getInstance().listConfigNames()
    ConfigBase(
        "pulsar",
        dialogState,
        errorState,
        dialogInputContent = {
            ConfigItemString(editPulsarName, R.strings.name, singleLine = true)
            DropdownList(kubernetes, "k8s instance", editK8sInstanceName)
            ConfigItemString(editPulsarNamespace, R.strings.namespace, singleLine = true)
            ConfigItemString(editPulsarDeployName, R.strings.statefulSetName, singleLine = true)
        },
        dialogConfirm = {
            StoragePulsar.getInstance().saveConfig(
                PulsarConfig(
                    editPulsarName.value,
                    editK8sInstanceName.value,
                    editPulsarNamespace.value,
                    editPulsarDeployName.value,
                )
            )
            pulsarList.value = StoragePulsar.getInstance().listContent()
        },
        content = {
            repeat(pulsarList.value.size) { it ->
                Row {
                    Text(pulsarList.value[it])
                    Button(
                        onClick = {
                            StoragePulsar.getInstance().deleteConfig(editPulsarName.value)
                            pulsarList.value = StoragePulsar.getInstance().listContent()
                        }
                    ) {
                        Text(R.strings.delete)
                    }
                }
            }
        }
    )
}
