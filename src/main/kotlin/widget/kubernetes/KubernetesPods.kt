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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.github.shoothzj.dev.module.config.KubernetesConfig
import com.github.shoothzj.dev.transfer.Transfer

@Composable
fun KubernetesPods(
    k8sConfig: KubernetesConfig
): @Composable Unit {

    var infoList: MutableList<String>
    val namespace = mutableStateOf("default")
    var namespaceInfo = Transfer().getNamespaceInfo(
        k8sConfig.sshStep.username, k8sConfig.sshStep.password,
        k8sConfig.host, k8sConfig.port
    )
    Column {
        Row {
            var expanded by remember { mutableStateOf(false) }
            var textFieldSize by remember { mutableStateOf(Size.Zero) }
            val icon = Icons.Filled.ArrowDropDown

            Column(modifier = Modifier.padding(bottom = 2.dp, top = 2.dp)) {
                OutlinedTextField(
                    value = namespace.value,
                    onValueChange = {
                        namespace.value = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        },
                    label = { Text("namespace") },
                    trailingIcon = {
                        Icon(
                            icon, "contentDescription",
                            Modifier.clickable { expanded = !expanded }
                        )
                    },
                    singleLine = true,
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                ) {
                    namespaceInfo.forEach { label ->
                        DropdownMenuItem(onClick = {
                            namespace.value = label
                            expanded = false
                            infoList = Transfer().getPodsInfo(
                                k8sConfig.sshStep.username, k8sConfig.sshStep.password,
                                k8sConfig.host, k8sConfig.port, namespace.value
                            )
                        }) {
                            Text(text = label)
                        }
                    }
                }
            }
        }
        Row {
            infoList = Transfer().getPodsInfo(
                k8sConfig.sshStep.username, k8sConfig.sshStep.password,
                k8sConfig.host, k8sConfig.port, namespace.value
            )
            repeat(infoList.size) {
                Row { Text(infoList[it], fontSize = 25.sp) }
            }
        }
    }
}
