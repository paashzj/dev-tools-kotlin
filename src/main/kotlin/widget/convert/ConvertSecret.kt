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

package widget.convert

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.github.shoothzj.dev.secret.ConvertSecret

@Composable
fun ConvertJks2Pem() {
    var trustStore by remember { mutableStateOf("") }
    var keyStore by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var path by remember { mutableStateOf("") }
    Row {
        Column(modifier = Modifier.verticalScroll(rememberScrollState()).weight(3f)) {
            Row {
                OutlinedTextField(
                    value = trustStore,
                    onValueChange = {
                        trustStore = it
                    },
                    label = { Text("trust store base64 content") }
                )
            }
        }
        Column(modifier = Modifier.verticalScroll(rememberScrollState()).weight(3f)) {
            Row {
                OutlinedTextField(
                    value = keyStore,
                    onValueChange = {
                        keyStore = it
                    },
                    label = { Text("key store base64 content") }
                )
            }
        }
        Column(modifier = Modifier.verticalScroll(rememberScrollState()).weight(4f)) {
            Row {
                Column {
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                        },
                        label = { Text("key password") }
                    )
                    OutlinedTextField(
                        value = path,
                        onValueChange = {
                            path = it
                        },
                        label = { Text(R.strings.generatePath) }
                    )
                    Button(onClick = {
                        val secret = ConvertSecret()
                        var result = secret.jks2pem(trustStore, keyStore, password, path)
                    }) {
                        Text(R.strings.generate)
                    }
                }
            }
        }
    }
}
