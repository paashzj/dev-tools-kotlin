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

package widget.trouble.lvs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.github.shoothzj.dev.storage.StorageLvs
import widget.component.DropdownList
import widget.config.ConfigItemHost
import widget.config.ConfigItemPort
import widget.config.ConfigItemString
import widget.trouble.TroubleShootBase

@Composable
fun TroubleLvsConnFail() {
    TroubleShootBase(
        content = {
            val lvsNameList = StorageLvs.getInstance().listConfigNames()
            DropdownList(lvsNameList, "lvs ${R.strings.instance}", editLvsInstanceName)
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
            ConfigItemString(
                editLvsNamespace,
                "namespace", singleLine = true
            )
            ConfigItemString(
                editLvsDeployName,
                "lvs deploy name", singleLine = true
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
            if (editLvsInstanceName.value != "") {
                val lvsConfig = StorageLvs.getInstance().getConfig(editLvsInstanceName.value)
                editLvsHost.value = lvsConfig.jumpHost
                editLvsPort.value = lvsConfig.jumpPort.toString()
                editLvsUsername.value = lvsConfig.jumpUsername
                editLvsPassword.value = lvsConfig.jumpPassword
                editLvsMasterHost.value = lvsConfig.masterHost
                editLvsSlaveHost.value = lvsConfig.slaveHost
            }
        },
        result = {
        },
    )
}
