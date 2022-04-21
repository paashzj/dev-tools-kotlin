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

import R.strings
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
import com.github.shoothzj.dev.transfer.Transfer
import com.github.shoothzj.dev.transfer.TransferResp
import navigationContext
import widget.TopBar
import widget.component.RowPaddingButton

@Composable
fun KubernetesInstanceScreen() {

    var targetPath by remember { mutableStateOf("") }
    var localFile by remember { mutableStateOf("") }
    var expended = mutableStateOf(false)
    var result: TransferResp? = null

    Column {
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
                    result =
                        Transfer().transferFile(
                            k8sConfig.sshStep.username, k8sConfig.sshStep.password,
                            k8sConfig.host, k8sConfig.port, localFile, targetPath
                        )
                    expended.value = true
                }
            ) { Text(text = strings.Transfer, fontSize = 35.sp) }
        }
        Column {
            if (expended.value) {
                if (result!!.code == 200) {
                    for (content in result!!.contents) {
                        Text(content.toString())
                    }
                    Text(result!!.reason)
                } else {
                    Text(result!!.reason)
                }
            }
        }
    }
}
