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
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.github.shoothzj.dev.module.config.Settings
import com.github.shoothzj.dev.storage.StorageSettings

@Composable
fun Settings() {
    val config = mutableStateOf(StorageSettings.getInstance().getConfig())
    var editSshJumpTimeoutSeconds by remember { mutableStateOf(config.value.sshJumpTimeoutSeconds.toString()) }
    var editSshExecuteTimeoutSeconds by remember { mutableStateOf(config.value.sshExecuteTimeoutSeconds.toString()) }
    var editSshloginTimeoutSeconds by remember { mutableStateOf(config.value.sshLoginTimeoutSeconds.toString()) }
    var res by remember { mutableStateOf("") }
    Column {
        Row {
            OutlinedTextField(
                value = editSshJumpTimeoutSeconds,
                onValueChange = {
                    editSshJumpTimeoutSeconds = it
                },
                label = { Text("ssh jump timeout seconds") }
            )
        }

        Row {
            OutlinedTextField(
                value = editSshExecuteTimeoutSeconds,
                onValueChange = {
                    editSshExecuteTimeoutSeconds = it
                },
                label = { Text("ssh execute timeout seconds") }
            )
        }

        Row {
            OutlinedTextField(
                value = editSshloginTimeoutSeconds,
                onValueChange = {
                    editSshloginTimeoutSeconds = it
                },
                label = { Text("ssh login timeout seconds") }
            )
        }

        Row {
            Button(
                onClick = {
                    val bool = StorageSettings.getInstance().saveConfig(
                        Settings(
                            editSshExecuteTimeoutSeconds,
                            editSshJumpTimeoutSeconds,
                            editSshloginTimeoutSeconds
                        )
                    )
                    if (bool) {
                        res = "save success."
                    } else { res = "save fail." }
                    config.value = StorageSettings.getInstance().getConfig()
                }
            ) {
                Text("apply")
            }
        }
        Row {
            Text(res)
        }
    }
}
