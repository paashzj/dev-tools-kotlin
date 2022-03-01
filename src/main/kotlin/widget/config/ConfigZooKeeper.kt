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
import com.github.shoothzj.dev.module.config.ZooKeeperConfig
import com.github.shoothzj.dev.storage.StorageK8s
import com.github.shoothzj.dev.storage.StorageZooKeeper
import widget.component.DropdownList

@Composable
fun ConfigZooKeeper() {
    val dialogState = mutableStateOf(false)
    val errorState = mutableStateOf("")
    val zooKeeperList = mutableStateOf(StorageZooKeeper.getInstance().listContent())
    val editZooKeeperName = mutableStateOf("default")
    val editK8sInstanceName = mutableStateOf("")
    val editZooKeeperNamespace = mutableStateOf("default")
    val editZooKeeperStatefulSetName = mutableStateOf("zookeeper")
    val kubernetes = StorageK8s.getInstance().listConfigNames()
    ConfigBase(
        "zookeeper",
        dialogState,
        errorState,
        dialogInputContent = {
            ConfigItemString(editZooKeeperName, R.strings.name)
            DropdownList(kubernetes, "k8s instance", editK8sInstanceName)
            ConfigItemString(editZooKeeperNamespace, R.strings.namespace)
            ConfigItemString(editZooKeeperStatefulSetName, R.strings.statefulSetName)
        },
        dialogConfirm = {
            StorageZooKeeper.getInstance().saveConfig(
                ZooKeeperConfig(
                    editZooKeeperName.value,
                    editK8sInstanceName.value,
                    editZooKeeperNamespace.value,
                    editZooKeeperStatefulSetName.value,
                )
            )
            zooKeeperList.value = StorageZooKeeper.getInstance().listContent()
        },
        content = {
            repeat(zooKeeperList.value.size) { it ->
                Row {
                    Text(zooKeeperList.value[it])
                    Button(
                        onClick = {
                            StorageZooKeeper.getInstance().deleteConfig(editZooKeeperName.value)
                            zooKeeperList.value = StorageZooKeeper.getInstance().listContent()
                        }
                    ) {
                        Text(R.strings.delete)
                    }
                }
            }
        }
    )
}
