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

package widget.simulator

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.sp
import module.SimulatorEnum
import widget.component.RowPaddingButton

val idx = mutableStateOf(SimulatorEnum.Kafka)

@Composable
fun Simulator() {
    Head(idx)
    when (idx.value) {
        SimulatorEnum.Kafka -> {
            SimulatorKafka()
        }
    }
}

@Composable
fun Head(idx: MutableState<SimulatorEnum>) {
    Row {
        Text("Simulator List", fontSize = 40.sp)
        RowPaddingButton(
            onClick = {
                idx.value = SimulatorEnum.Kafka
            },
        ) {
            Text(text = "kafka")
        }
    }
}
