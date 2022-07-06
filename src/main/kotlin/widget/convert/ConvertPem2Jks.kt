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
fun ConvertPem2Jks() {
    var certFilePath by remember { mutableStateOf("") }
    var keyFilePath by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var outputPath by remember { mutableStateOf("") }
    Row {
        Column(modifier = Modifier.verticalScroll(rememberScrollState()).weight(3f)) {
            Row {
                OutlinedTextField(
                    value = certFilePath,
                    onValueChange = {
                        certFilePath = it
                    },
                    label = { Text("cert file path") }
                )
            }
        }
        Column(modifier = Modifier.verticalScroll(rememberScrollState()).weight(3f)) {
            Row {
                OutlinedTextField(
                    value = keyFilePath,
                    onValueChange = {
                        keyFilePath = it
                    },
                    label = { Text("key file path") }
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
                        value = outputPath,
                        onValueChange = {
                            outputPath = it
                        },
                        label = { Text(R.strings.generatePath) }
                    )
                    Button(onClick = {
                        val secret = ConvertSecret()
                        secret.pem2jks(certFilePath, keyFilePath, password)
                    }) {
                        Text(R.strings.generate)
                    }
                }
            }
            Row {
                Column {
                    OutlinedTextField(
                        value = "openssl pkcs12 -in trust.p12 -nokeys -out ca.pem",
                        onValueChange = {
                        },
                        label = { Text("generate ca cert command") }
                    )
                    OutlinedTextField(
                        value = "openssl pkcs12 -in key.p12 -nokeys -out cert.pem",
                        onValueChange = {
                        },
                        label = { Text("generate cert command") }
                    )
                    OutlinedTextField(
                        value = "openssl pkcs12 -in key.p12 -nodes -nocerts -out private.pem",
                        onValueChange = {
                        },
                        label = { Text("generate private key command") }
                    )
                }
            }
        }
    }
}
