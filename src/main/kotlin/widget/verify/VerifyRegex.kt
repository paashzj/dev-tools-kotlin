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

package widget.verify

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.regex.Pattern

@Composable
fun VerifyRegex() {
    var text by remember { mutableStateOf("") }
    var regex by remember { mutableStateOf("") }
    var result by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                result = checkMatch(text, regex)
            },
            label = { Text(R.strings.content) }
        )
        OutlinedTextField(
            value = regex,
            onValueChange = {
                regex = it
                result = checkMatch(text, regex)
            },
            label = { Text(R.strings.regexExpression) }
        )
        Text(if (result) R.strings.match else R.strings.notMatch, fontSize = 30.sp, modifier = Modifier.padding(10.dp))
    }
}

fun checkMatch(text: String, regex: String): Boolean {
    return try {
        Pattern.compile(regex).matcher(text).matches()
    } catch (e: Exception) {
        false
    }
}
