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
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import module.config.KubernetesConfig

val showKubernetesCreateDialog = mutableStateOf(false)

@Composable
fun ConfigKubernetes() {
    Column {
        if (showKubernetesCreateDialog.value) {
            Dialog(
                onCloseRequest = {
                    showKubernetesCreateDialog.value = false
                },
                title = "Add kubernetes instance",
            ) {
                val editElem = remember {
                    mutableStateOf(
                        KubernetesConfig(
                            name = "default",
                            host = "localhost",
                            port = 22,
                            username = "root",
                            password = "toor",
                            rootPassword = null,
                        )
                    )
                }
                MaterialTheme {
                    OutlinedTextField(
                        value = editElem.value.name,
                        onValueChange = {
                            editElem.value.name = it
                        },
                        label = { Text("config name") }
                    )
                }
            }
        }
        Button(onClick = {
            showKubernetesCreateDialog.value = true
        }) {
            Text("add kubernetes")
        }
        Text("kubernetes list:", fontSize = 30.sp)
    }
}
