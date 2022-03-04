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

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.github.shoothzj.dev.module.config.RedisConfig
import com.github.shoothzj.dev.storage.StorageK8s
import com.github.shoothzj.dev.storage.StorageRedis
import widget.component.DropdownList

@Composable
fun ConfigRedis() {
    val dialogState = mutableStateOf(false)
    val errorState = mutableStateOf("")
    val redisList = mutableStateOf(StorageRedis.getInstance().listContent())
    val editRedisName = mutableStateOf("default")
    val editK8sInstanceName = mutableStateOf("")
    val editRedisNamespace = mutableStateOf("default")
    val editRedisStatefulSetName = mutableStateOf("redis")
    val kubernetes = StorageK8s.getInstance().listConfigNames()
    ConfigBase(
        "redis",
        dialogState,
        errorState,
        dialogInputContent = {
            ConfigItemString(editRedisName, R.strings.name, singleLine = true)
            DropdownList(kubernetes, "k8s ${R.strings.instance}", editK8sInstanceName)
            ConfigGroupStatefulSet(
                editRedisNamespace,
                editRedisStatefulSetName,
            )
        },
        dialogConfirm = {
            StorageRedis.getInstance().saveConfig(
                RedisConfig(
                    editRedisName.value,
                    editK8sInstanceName.value,
                    editRedisNamespace.value,
                    editRedisStatefulSetName.value,
                )
            )
            redisList.value = StorageRedis.getInstance().listContent()
        },
        content = {
            repeat(redisList.value.size) { it ->
                Row {
                    Text(redisList.value[it])
                    Button(
                        onClick = {
                            StorageRedis.getInstance().deleteConfig(editRedisName.value)
                            redisList.value = StorageRedis.getInstance().listContent()
                        }
                    ) {
                        Text(R.strings.delete)
                    }
                }
            }
        }
    )
}
