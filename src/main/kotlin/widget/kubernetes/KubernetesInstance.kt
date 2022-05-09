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

package widget.kubernetes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import com.github.shoothzj.dev.module.config.KubernetesConfig
import com.github.shoothzj.dev.state.State
import com.github.shoothzj.dev.transfer.Transfer
import com.github.shoothzj.dev.transfer.TransferResp
import navigationContext
import widget.TopBar
import widget.component.RowPaddingButton
import widget.component.ShowBase

@Composable
fun KubernetesInstanceScreen() {

    var targetPath by remember { mutableStateOf("") }
    var masterFile by remember { mutableStateOf("") }
    var remotePath by remember { mutableStateOf("") }
    var localFile by remember { mutableStateOf("") }
    var command by remember { mutableStateOf("") }
    var resource by remember { mutableStateOf("") }
    var target by remember { mutableStateOf("") }
    var filename by remember { mutableStateOf("") }
    val expended = mutableStateOf(false)
    var result: TransferResp? = null

    ShowBase(
        leftContent = {
            val k8sConfig: KubernetesConfig = navigationContext.value as KubernetesConfig
            TopBar()
            Text(text = "kubernetes ${k8sConfig.name}", fontSize = 40.sp)
            Column {
                Row {
                    Column {
                        OutlinedTextField(
                            value = localFile,
                            onValueChange = {
                                localFile = it
                            },
                            label = { Text("file path") }
                        )
                    }

                    Column {
                        OutlinedTextField(
                            value = targetPath,
                            onValueChange = {
                                targetPath = it
                            },
                            label = { Text("target path") }
                        )
                    }
                }
                RowPaddingButton(
                    onClick = {
                        expended.value = true
                        result =
                            Transfer().localTransfer(
                                k8sConfig.sshStep.username, k8sConfig.sshStep.password,
                                k8sConfig.host, k8sConfig.port, localFile, targetPath
                            )
                    }
                ) { Text(text = R.strings.localTransfer, fontSize = 35.sp) }
            }
            // master publish
            Column {
                Row {
                    Column {
                        OutlinedTextField(
                            value = masterFile,
                            onValueChange = {
                                masterFile = it
                            },
                            label = { Text("master file") }
                        )
                    }

                    Column {
                        OutlinedTextField(
                            value = remotePath,
                            onValueChange = {
                                remotePath = it
                            },
                            label = { Text("remote path") }
                        )
                    }
                }
                Row {
                    RowPaddingButton(
                        onClick = {
                            result =
                                Transfer().masterTransfer(
                                    k8sConfig.sshStep.username, k8sConfig.sshStep.password,
                                    k8sConfig.host, k8sConfig.port, masterFile, remotePath
                                )
                            expended.value = true
                        }
                    ) { Text(text = R.strings.broadcast, fontSize = 35.sp) }
                }
            }
            // command
            Column {
                Row {
                    Column {
                        OutlinedTextField(
                            value = command,
                            onValueChange = {
                                command = it
                            },
                            label = { Text("command") }
                        )
                    }
                }
                Row {
                    RowPaddingButton(
                        onClick = {
                            result = Transfer().execute(
                                k8sConfig.sshStep.username, k8sConfig.sshStep.password,
                                k8sConfig.host, k8sConfig.port, command
                            )
                            expended.value = true
                        }
                    ) { Text(text = R.strings.execute, fontSize = 35.sp) }
                }
            }
            //  sed command
            Column {
                Row {
                    Column {
                        OutlinedTextField(
                            value = resource,
                            onValueChange = {
                                resource = it
                            },
                            label = { Text("resource word") }
                        )
                    }
                    Column {
                        OutlinedTextField(
                            value = target,
                            onValueChange = {
                                target = it
                            },
                            label = { Text("target word") }
                        )
                    }
                }
                Row {
                    Column {
                        OutlinedTextField(
                            value = filename,
                            onValueChange = {
                                filename = it
                            },
                            label = { Text("filename") }
                        )
                    }
                }
                Row {
                    RowPaddingButton(
                        onClick = {
                            result = Transfer().replaceWord(
                                k8sConfig.sshStep.username, k8sConfig.sshStep.password,
                                k8sConfig.host, k8sConfig.port, resource, target, filename
                            )
                            expended.value = true
                        }
                    ) { Text(text = R.strings.ChangeWord, fontSize = 35.sp) }
                }
            }
        },
        rightContent = {
            val k8sConfig: KubernetesConfig = navigationContext.value as KubernetesConfig
            val nodeInfoList = Transfer().getNodeInfo(
                k8sConfig.sshStep.username, k8sConfig.sshStep.password,
                k8sConfig.host, k8sConfig.port
            )
            repeat(nodeInfoList.size) {
                Row { Text(nodeInfoList[it], fontSize = 25.sp) }
            }
        },
        result = {
            if (expended.value) {
                if (result!!.code == State.HASCONTENT.code) {
                    for (content in result!!.contents) {
                        Text(content, fontSize = 25.sp)
                    }
                    val nodeInfos = result!!.nodeInfos
                    repeat(nodeInfos.size) { idx ->
                        val nodeInfo = nodeInfos[idx]
                        val res = nodeInfo.executeResult
                        Text(nodeInfos[idx].name, fontSize = 25.sp)
                        repeat(res.size) { iidx ->
                            Text(res[iidx])
                        }
                    }

                    Text(result!!.reason, fontSize = 30.sp)
                } else {
                    Text(result!!.reason, fontSize = 30.sp)
                }
            }
        },
    )
}
