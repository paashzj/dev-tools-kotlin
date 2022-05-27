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
    val config = mutableStateOf(StorageSettings.getConfig())
    var editSshJumpTimeoutSeconds by remember { mutableStateOf(config.value.sshJumpTimeoutSeconds.toString()) }
    var editSshExecuteTimeoutSeconds by remember { mutableStateOf(config.value.sshExecuteTimeoutSeconds.toString()) }
    var editSshloginTimeoutSeconds by remember { mutableStateOf(config.value.sshLoginTimeoutSeconds.toString()) }
    var editJdk8X86FilePath by remember { mutableStateOf(config.value.jdk8X86FilePath.toString()) }
    var editJdk11X86FilePath by remember { mutableStateOf(config.value.jdk11X86FilePath.toString()) }
    var editJdk17X86FilePath by remember { mutableStateOf(config.value.jdk17X86FilePath.toString()) }
    var editJdk8armFilePath by remember { mutableStateOf(config.value.jdk8armFilePath.toString()) }
    var editJdk11armFilePath by remember { mutableStateOf(config.value.jdk11armFilePath.toString()) }
    var editJdk17armFilePath by remember { mutableStateOf(config.value.jdk17armFilePath.toString()) }
    var editDumpFileDir by remember { mutableStateOf(config.value.dumpFileDir.toString()) }
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
            OutlinedTextField(
                value = editJdk8X86FilePath,
                onValueChange = {
                    editJdk8X86FilePath = it
                },
                label = { Text("jdk 8 x86 file path") }
            )
        }

        Row {
            OutlinedTextField(
                value = editJdk11X86FilePath,
                onValueChange = {
                    editJdk11X86FilePath = it
                },
                label = { Text("jdk 11 x86 file path") }
            )
        }

        Row {
            OutlinedTextField(
                value = editJdk17X86FilePath,
                onValueChange = {
                    editJdk17X86FilePath = it
                },
                label = { Text("jdk 17 x86 file path") }
            )
        }

        Row {
            OutlinedTextField(
                value = editJdk8armFilePath,
                onValueChange = {
                    editJdk8armFilePath = it
                },
                label = { Text("jdk 8 arm file path") }
            )
        }

        Row {
            OutlinedTextField(
                value = editJdk11armFilePath,
                onValueChange = {
                    editJdk11armFilePath = it
                },
                label = { Text("jdk 11 arm file path") }
            )
        }

        Row {
            OutlinedTextField(
                value = editJdk17armFilePath,
                onValueChange = {
                    editJdk17armFilePath = it
                },
                label = { Text("jdk 17 arm file path") }
            )
        }

        Row {
            OutlinedTextField(
                value = editDumpFileDir,
                onValueChange = {
                    editDumpFileDir = it
                },
                label = { Text("java dump file dir") }
            )
        }

        Row {
            Button(
                onClick = {
                    val bool = StorageSettings.saveConfig(
                        Settings(
                            editSshExecuteTimeoutSeconds,
                            editSshJumpTimeoutSeconds,
                            editSshloginTimeoutSeconds,
                            editJdk8X86FilePath,
                            editJdk11X86FilePath,
                            editJdk17X86FilePath,
                            editJdk8armFilePath,
                            editJdk11armFilePath,
                            editJdk17armFilePath,
                            editDumpFileDir,
                        )
                    )
                    res = if (bool) {
                        "save success."
                    } else {
                        "save fail."
                    }
                    config.value = StorageSettings.getConfig()
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
