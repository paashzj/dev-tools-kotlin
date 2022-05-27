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

package widget.trouble.redis

import androidx.compose.runtime.Composable
import com.github.shoothzj.dev.storage.StorageK8s
import com.github.shoothzj.dev.storage.StorageRedis
import widget.component.DropdownList
import widget.config.ConfigGroupKubernetes
import widget.config.ConfigGroupStatefulSet
import widget.trouble.TroubleShootBase

@Composable
fun TroubleRedisClusterInitFail() {
    TroubleShootBase(
        content = {
            val redisNameList = StorageRedis.getInstance().listConfigNames()
            DropdownList(redisNameList, "redis ${R.strings.instance}", editRedisInstanceName)
            ConfigGroupKubernetes(
                editKubernetesHost,
                editKubernetesPort,
                editKubernetesUsername,
                editKubernetesPassword,
                editKubernetesRootPassword
            )
            ConfigGroupStatefulSet(
                editRedisNamespace,
                editRedisStatefulSetName,
            )
            if (editRedisInstanceName.value != "") {
                val redisConfig = StorageRedis.getInstance().getConfig(editRedisInstanceName.value)
                editRedisNamespace.value = redisConfig.namespace
                editRedisStatefulSetName.value = redisConfig.statefulSetName
                val kubernetesConfig = StorageK8s.getInstance().getConfig(redisConfig.k8sName)
                editKubernetesHost.value = kubernetesConfig.host
                editKubernetesPort.value = kubernetesConfig.port.toString()
                val sshStep = kubernetesConfig.sshStep
                editKubernetesUsername.value = sshStep.username
                editKubernetesPassword.value = sshStep.password
                editKubernetesRootPassword.value = if (sshStep.suPassword == null) "" else sshStep.suPassword
            }
        },
        result = {
        },
    )
}
