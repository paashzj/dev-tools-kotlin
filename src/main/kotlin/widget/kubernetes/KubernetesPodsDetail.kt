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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import backContext
import com.github.shoothzj.dev.dump.DumpAction
import com.github.shoothzj.dev.module.UiResponse
import com.github.shoothzj.dev.storage.StorageK8s
import com.github.shoothzj.dev.util.K8sSshUtil
import module.NavigationEnum
import navigationContext
import navigationIdx
import widget.component.RowPaddingButton

@Composable
fun KubernetesPodsDetailScreen() {
    val value: List<String> = navigationContext.value as List<String>
    val podName = value[0]
    val namespace = value[1]
    val k8sConfig = StorageK8s.getInstance().deserializeConfig(backContext.value.toString())
    val dialogState = mutableStateOf(false)
    var result: MutableState<UiResponse<String>> = mutableStateOf(UiResponse<String>())
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Row {
            RowPaddingButton(
                onClick = {
                    navigationContext.value = k8sConfig
                    navigationIdx.value = NavigationEnum.Kubernetes
                },
            ) {
                Text(text = R.strings.back)
            }
        }
        val detail = K8sSshUtil().getPodsDetail(
            k8sConfig.sshStep.username, k8sConfig.sshStep.password,
            k8sConfig.host, k8sConfig.port, podName, namespace
        )
        Text(text = R.strings.details, fontSize = 45.sp)
        if (detail.size != 0) {
            repeat(detail.size) {
                if (detail[it].cmd.contains("java")) {
                    Row {
                        Column(modifier = Modifier.verticalScroll(rememberScrollState()).weight(3f)) {
                            Row {
                                Text(
                                    detail[it].toString().substring(16, detail[it].toString().length - 1),
                                    modifier = Modifier.padding(15.dp)
                                )
                            }
                        }
                        Column(modifier = Modifier.verticalScroll(rememberScrollState()).weight(1f)) {
                            Row {
                                Button(
                                    onClick = {
                                        result.value = DumpAction().dump(
                                            k8sConfig.host, k8sConfig.port, k8sConfig.sshStep.username,
                                            k8sConfig.sshStep.password, podName, namespace, detail[it].pid
                                        )
                                        dialogState.value = true
                                    }
                                ) {
                                    Text("dump")
                                }
                            }
                        }
                    }
                } else {
                    Text(
                        detail[it].toString().substring(16, detail[it].toString().length - 1),
                        modifier = Modifier.padding(15.dp)
                    )
                }
            }
        } else {
            Text("failed to get the pod container process.")
        }
        PodsDetailResult(dialogState, result)
    }
}
