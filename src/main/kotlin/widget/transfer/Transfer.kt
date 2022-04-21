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

package widget.transfer

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
import com.github.shoothzj.dev.transfer.Transfer
import widget.component.RowPaddingButton

@Composable
fun TransferScreen() {

    var configHost by remember { mutableStateOf("") }
    var configPort by remember { mutableStateOf("") }
    var sshUserName by remember { mutableStateOf("") }
    var sshPassword by remember { mutableStateOf("") }
    var targetPath by remember { mutableStateOf("") }
    var localFile by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column {
        RowPaddingButton(
            onClick = {
                result =
                    Transfer().transferFile(sshUserName, sshPassword, configHost, configPort, localFile, targetPath)
            }
        ) { Text(text = R.strings.send, fontSize = 12.sp) }
        Row {
            Column {
                OutlinedTextField(
                    value = configHost,
                    onValueChange = {
                        configHost = it
                    },
                    label = { Text("config host") }
                )
            }
            Column {
                OutlinedTextField(
                    value = configPort,
                    onValueChange = {
                        configPort = it
                    },
                    label = { Text("config port") }
                )
            }
            Column {
                OutlinedTextField(
                    value = sshUserName,
                    onValueChange = {
                        sshUserName = it
                    },
                    label = { Text("ssh username") }
                )
            }
            Column {
                OutlinedTextField(
                    value = sshPassword,
                    onValueChange = {
                        sshPassword = it
                    },
                    label = { Text("ssh password") }
                )
            }
        }
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
    }
    Text(result)
}
