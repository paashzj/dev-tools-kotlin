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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import util.Base64Util

@Composable
fun ConvertBase64() {
    var rawText by remember { mutableStateOf("") }
    var base64Text by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = rawText,
            onValueChange = {
                rawText = it
            },
            label = { Text("Raw Text") }
        )
        Row {
            TextButton(onClick = {
                base64Text = Base64Util.encode(rawText)
            }) {
                Text("Base64 encode")
            }
            TextButton(onClick = {
                rawText = Base64Util.decode(base64Text)
            }) {
                Text("Base64 decode")
            }
        }
        OutlinedTextField(
            value = base64Text,
            onValueChange = {
                base64Text = it
            },
            label = { Text("Base64 Text") }
        )
    }
}
