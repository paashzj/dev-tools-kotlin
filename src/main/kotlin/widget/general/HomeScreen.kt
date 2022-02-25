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

package widget.general

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.sp
import module.ToolEnum
import widget.command.Command
import widget.component.RowPaddingButton
import widget.convert.Convert
import widget.simulator.Simulator

val idx = mutableStateOf(ToolEnum.Command)

@Composable
fun HomeScreen() {
    Column {
        Head(idx)
        when (idx.value) {
            ToolEnum.Command -> {
                Command()
            }
            ToolEnum.Convert -> {
                Convert()
            }
            ToolEnum.Simulator -> {
                Simulator()
            }
        }
    }
}

@Composable
fun Head(idx: MutableState<ToolEnum>) {
    Row {
        Text("Tool List", fontSize = 50.sp)
        RowPaddingButton(
            onClick = {
                idx.value = ToolEnum.Command
            },
        ) {
            Text(text = "command")
        }
        RowPaddingButton(
            onClick = {
                idx.value = ToolEnum.Convert
            },
        ) {
            Text(text = "convert")
        }
        RowPaddingButton(
            onClick = {
                idx.value = ToolEnum.Simulator
            },
        ) {
            Text(text = "simulator")
        }
    }
}
