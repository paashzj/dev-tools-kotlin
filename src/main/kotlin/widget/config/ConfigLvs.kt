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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.shoothzj.dev.module.config.LvsConfig
import com.github.shoothzj.dev.storage.StorageLvs
import module.NavigationEnum
import navigationContext
import navigationIdx

@Composable
fun ConfigLvs() {
    val dialogState = mutableStateOf(false)
    val errorState = mutableStateOf("")
    val lvsList = mutableStateOf(StorageLvs.getInstance().listContent())
    val editLvsName = mutableStateOf("default")
    val editLvsHost = mutableStateOf("localhost")
    val editLvsPort = mutableStateOf("22")
    val editLvsUsername = mutableStateOf("root")
    val editLvsPassword = mutableStateOf("")
    val editLvsMasterHost = mutableStateOf("")
    val editLvsSlaveHost = mutableStateOf("")
    ConfigBase(
        "lvs",
        dialogState,
        errorState,
        dialogInputContent = {
            ConfigItemString(editLvsName, R.strings.name, singleLine = true)
            Column {
                ConfigItemHost(
                    editLvsHost,
                    "config host", mutableStateOf("")
                )
                ConfigItemPort(
                    editLvsPort,
                    "config port", mutableStateOf("")
                )
                ConfigItemString(
                    editLvsUsername,
                    "ssh username", singleLine = true
                )
                ConfigItemString(
                    editLvsPassword,
                    "ssh password", singleLine = true
                )
                ConfigItemHost(
                    editLvsMasterHost,
                    "lvs master host",
                    mutableStateOf("")
                )
                ConfigItemHost(
                    editLvsSlaveHost,
                    "lvs slave host",
                    mutableStateOf("")
                )
            }
        },
        dialogConfirm = {
            StorageLvs.getInstance().saveConfig(
                LvsConfig(
                    editLvsName.value,
                    editLvsHost.value,
                    editLvsPort.value.toInt(),
                    editLvsUsername.value,
                    editLvsPassword.value,
                    editLvsMasterHost.value,
                    editLvsSlaveHost.value,
                )
            )
            lvsList.value = StorageLvs.getInstance().listContent()
        },
        content = {
            repeat(lvsList.value.size) { it ->
                Row {
                    Box(
                        modifier = Modifier.clickable {
                            navigationContext.value = StorageLvs.getInstance().deserializeConfig(lvsList.value[it])
                            navigationIdx.value = NavigationEnum.Lvs
                        }
                    ) {
                        Text(lvsList.value[it], modifier = Modifier.padding(15.dp))
                    }
                    Button(
                        onClick = {
                            StorageLvs.getInstance().deleteConfig(editLvsName.value)
                            lvsList.value = StorageLvs.getInstance().listContent()
                        }
                    ) {
                        Text(R.strings.delete)
                    }
                }
            }
        }
    )
}
