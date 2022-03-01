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

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

@Composable
fun ConfigGroupKubernetes(
    editKubernetesHost: MutableState<String>,
    editKubernetesPort: MutableState<String>,
    editKubernetesUsername: MutableState<String>,
    editKubernetesPassword: MutableState<String>,
    editKubernetesRootPassword: MutableState<String>
) {
    Column {
        ConfigItemHost(editKubernetesHost, "config host", mutableStateOf(""))
        ConfigItemPort(editKubernetesPort, "config port", mutableStateOf(""))
        ConfigItemString(editKubernetesUsername, "ssh username")
        ConfigItemString(editKubernetesPassword, "ssh password")
        ConfigItemString(editKubernetesRootPassword, "ssh root password(if you need to switch root)")
    }
}
