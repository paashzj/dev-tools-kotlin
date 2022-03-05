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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.rememberDialogState
import constant.PixelConst
import constant.PixelConst.Companion.configDialogSize

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ConfigBase(
    configName: String,
    dialogState: MutableState<Boolean>,
    errorTextState: MutableState<String>,
    dialogInputContent: @Composable () -> Unit,
    dialogConfirm: () -> Unit,
    content: @Composable () -> Unit,
): @Composable Unit {
    Column {
        if (errorTextState.value != "") {
            AlertDialog(
                modifier = Modifier.size(PixelConst.configErrorDialogSize),
                onDismissRequest = {},
                title = { Text("Error during config") },
                confirmButton = {
                    MaterialTheme {
                        Button(onClick = {
                            errorTextState.value = ""
                        }) {
                            Text(R.strings.confirm)
                        }
                    }
                },
                text = { Text(errorTextState.value) }
            )
        } else if (dialogState.value) {
            Dialog(
                onCloseRequest = {
                    dialogState.value = false
                },
                state = rememberDialogState(size = configDialogSize),
                title = "${R.strings.add} $configName ${R.strings.instance}",
            ) {
                MaterialTheme {
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        dialogInputContent()
                        Row {
                            Button(onClick = {
                                dialogState.value = false
                            }) {
                                Text(R.strings.dismiss)
                            }
                            Button(onClick = {
                                dialogState.value = false
                                dialogConfirm()
                                // save the kubernetes config
                            }) {
                                Text(R.strings.confirm)
                            }
                        }
                    }
                }
            }
        }
        Button(onClick = {
            dialogState.value = true
        }) {
            Text("${R.strings.add} $configName")
        }
        Text("$configName list:", fontSize = 30.sp)
        content()
    }
}
