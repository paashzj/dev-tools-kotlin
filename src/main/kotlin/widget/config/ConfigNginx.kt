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

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.github.shoothzj.dev.module.config.NginxConfig
import com.github.shoothzj.dev.storage.StorageNginx

@Composable
fun ConfigNginx() {
    val dialogState = mutableStateOf(false)
    val errorState = mutableStateOf("")
    val nginxList = mutableStateOf(StorageNginx.getInstance().listContent())
    val editNginxName = mutableStateOf("default")
    val editNginxNamespace = mutableStateOf("default")
    val editNginxDeployName = mutableStateOf("nginx")
    ConfigBase(
        "nginx",
        dialogState,
        errorState,
        dialogInputContent = {
            ConfigItemString(editNginxNamespace, "default")
            ConfigItemString(editNginxDeployName, "nginx")
        },
        dialogConfirm = {
            StorageNginx.getInstance().saveConfig(
                NginxConfig(
                    editNginxName.value,
                    editNginxNamespace.value,
                    editNginxDeployName.value,
                )
            )
            nginxList.value = StorageNginx.getInstance().listContent()
        },
        content = {
            repeat(nginxList.value.size) { it ->
                Text(nginxList.value[it])
            }
        }
    )
}