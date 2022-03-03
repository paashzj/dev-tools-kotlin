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

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun ConfigItemBase(
    inputState: MutableState<String>,
    desc: String,
    verify: (String) -> String,
    errorState: MutableState<String>,
    singleLine: Boolean = false,
) {
    OutlinedTextField(
        value = inputState.value,
        onValueChange = {
            inputState.value = it
            errorState.value = verify(it)
        },
        isError = errorState.value != "",
        label = { Text(if (errorState.value != "") errorState.value else desc) },
        singleLine = singleLine,
    )
}
