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

package widget.command

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import module.CommandDto
import module.CommandParam

@Composable
fun CommandView(params: Array<CommandParam>, commandDto: CommandDto) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Text(R.strings.Params, fontSize = 40.sp)
        repeat(params.size) { it ->
            val commandParam = params[it]
            Row {
                Text(commandParam.name + ":")
                TextField(
                    value = commandParam.value.value,
                    onValueChange = {
                        commandParam.value.value = it
                    },
                )
            }
        }
        Text(R.strings.Commands, fontSize = 40.sp)
        repeat(commandDto.subCommandsList.size) { it ->
            val subCommands = commandDto.subCommandsList[it]
            Text(subCommands.category, fontSize = 30.sp)
            repeat(subCommands.commands.size) { iit ->
                val command = subCommands.commands[iit]
                // let we do the replacement
                var aux = command.template
                for (param in params) {
                    aux = aux.replace("$" + param.name, param.value.value)
                }
                command.value.value = aux
                OutlinedTextField(
                    value = command.value.value,
                    onValueChange = {
                        command.value.value = it
                    },
                    label = { Text(command.desc) }
                )
            }
        }
    }
}
