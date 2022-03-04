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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.shoothzj.dev.module.config.CassandraConfig
import com.github.shoothzj.dev.storage.StorageCassandra
import com.github.shoothzj.dev.storage.StorageK8s
import module.NavigationEnum
import navigationContext
import navigationIdx
import widget.component.DropdownList

@Composable
fun ConfigCassandra() {
    val dialogState = mutableStateOf(false)
    val errorState = mutableStateOf("")
    val cassandraList = mutableStateOf(StorageCassandra.getInstance().listContent())
    val editCassandraName = mutableStateOf("default")
    val editK8sInstanceName = mutableStateOf("")
    val editCassandraNamespace = mutableStateOf("default")
    val editCassandraStatefulSetName = mutableStateOf("cassandra")
    val kubernetes = StorageK8s.getInstance().listConfigNames()
    ConfigBase(
        "cassandra",
        dialogState,
        errorState,
        dialogInputContent = {
            ConfigItemString(editCassandraName, R.strings.name, singleLine = true)
            DropdownList(kubernetes, "k8s ${R.strings.instance}", editK8sInstanceName)
            ConfigGroupStatefulSet(
                editCassandraNamespace,
                editCassandraStatefulSetName,
            )
        },
        dialogConfirm = {
            StorageCassandra.getInstance().saveConfig(
                CassandraConfig(
                    editCassandraName.value,
                    editK8sInstanceName.value,
                    editCassandraNamespace.value,
                    editCassandraStatefulSetName.value,
                )
            )
            cassandraList.value = StorageCassandra.getInstance().listContent()
        },
        content = {
            repeat(cassandraList.value.size) { it ->
                Row {
                    Box(
                        modifier = Modifier.clickable {
                            navigationContext.value = StorageCassandra.getInstance().getConfig(editCassandraName.value)
                            navigationIdx.value = NavigationEnum.Cassandra
                        }
                    ) {
                        Text(cassandraList.value[it], modifier = Modifier.padding(15.dp))
                    }
                    Button(
                        onClick = {
                            StorageCassandra.getInstance().deleteConfig(editCassandraName.value)
                            cassandraList.value = StorageCassandra.getInstance().listContent()
                        }
                    ) {
                        Text(R.strings.delete)
                    }
                }
            }
        }
    )
}
