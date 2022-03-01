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

package widget.trouble.zookeeper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.github.shoothzj.dev.storage.StorageK8s
import com.github.shoothzj.dev.storage.StorageZooKeeper
import widget.component.DropdownList
import widget.config.ConfigItemPort
import widget.config.ConfigItemString

@Composable
fun TroubleZooKeeperClusterInitFail() {
    val zooKeeperNameList = StorageZooKeeper.getInstance().listConfigNames()
    DropdownList(zooKeeperNameList, "zookeeper instance", editZooKeeperInstanceName)
    ConfigItemString(editKubernetesHost, "k8s host")
    ConfigItemPort(editKubernetesPort, "k8s port", mutableStateOf(""))
    ConfigItemString(editKubernetesUsername, "ssh username")
    ConfigItemString(editKubernetesPassword, "ssh password")
    ConfigItemString(editKubernetesRootPassword, "ssh root password(if you need to switch root)")
    ConfigItemString(editZooKeeperNamespace, R.strings.namespace)
    ConfigItemString(editZooKeeperStatefulSetName, R.strings.statefulSetName)
    if (editZooKeeperInstanceName.value != "") {
        val zooKeeperConfig = StorageZooKeeper.getInstance().getConfig(editZooKeeperInstanceName.value)
        editZooKeeperNamespace.value = zooKeeperConfig.namespace
        editZooKeeperStatefulSetName.value = zooKeeperConfig.statefulSetName
        val kubernetesConfig = StorageK8s.getInstance().getConfig(zooKeeperConfig.k8sName)
        editKubernetesHost.value = kubernetesConfig.host
        editKubernetesPort.value = kubernetesConfig.port.toString()
        val sshStep = kubernetesConfig.sshStep
        editKubernetesUsername.value = sshStep.username
        editKubernetesPassword.value = sshStep.password
        editKubernetesRootPassword.value = if (sshStep.suPassword == null) "" else sshStep.suPassword
    }
}