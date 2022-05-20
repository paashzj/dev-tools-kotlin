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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import com.github.shoothzj.dev.module.config.KubernetesConfig
import navigationContext
import widget.TopBar
import widget.component.RowPaddingButton

@Composable
fun KubernetesInstanceScreen() {
    var buttoms by remember { mutableStateOf("nodes") }
    val k8sConfig: KubernetesConfig = navigationContext.value as KubernetesConfig

    Column {

        Row {
            TopBar()
            RowPaddingButton(
                onClick = {
                    buttoms = "nodes"
                },
            ) {
                Text(text = "nodes")
            }

            RowPaddingButton(
                onClick = {
                    buttoms = "pods"
                },
            ) {
                Text(text = "pods")
            }
        }
        Row {
            Text(text = "kubernetes ${k8sConfig.name}", fontSize = 40.sp)
        }
        Row {
            if (buttoms.equals("nodes")) {
                KubernetesNodes(
                    k8sConfig
                )
            }

            if (buttoms.equals("pods")) {
                KubernetesPods(
                    k8sConfig
                )
            }
        }
    }
}
