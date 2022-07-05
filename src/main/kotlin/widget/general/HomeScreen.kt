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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import module.NavigationEnum
import navigationIdx

@Composable
fun HomeScreen() {
    Column {
        Text(R.strings.AppWelcomeMessage, fontSize = 50.sp)
        Text(R.strings.AppDescMessage, fontSize = 30.sp)
        Row(
            modifier = Modifier.padding(all = 32.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier.weight(1f).height(150.dp).padding(8.dp),
                backgroundColor = Color.Cyan,
                shape = RoundedCornerShape(6.dp),
                elevation = 6.dp
            ) {
                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    TextButton(
                        onClick = {
                            navigationIdx.value = NavigationEnum.Command
                        },
                        modifier = Modifier.size(width = 400.dp, height = 50.dp),
                        content = {
                            Text(R.strings.commandPage, fontSize = 24.sp)
                        }
                    )
                }
            }
            Card(
                modifier = Modifier.weight(1f).height(150.dp).padding(8.dp),
                backgroundColor = Color.Green,
                shape = RoundedCornerShape(6.dp),
                elevation = 6.dp
            ) {
                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    TextButton(
                        onClick = {
                            navigationIdx.value = NavigationEnum.Simulator
                        },
                        modifier = Modifier.size(width = 400.dp, height = 50.dp),
                        content = {
                            Text(R.strings.simulatorPage, fontSize = 24.sp)
                        }
                    )
                }
            }
            Card(
                modifier = Modifier.weight(1f).height(150.dp).padding(8.dp),
                backgroundColor = Color.Yellow,
                shape = RoundedCornerShape(6.dp),
                elevation = 6.dp
            ) {
                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    TextButton(
                        onClick = {
                            navigationIdx.value = NavigationEnum.Component
                        },
                        modifier = Modifier.size(width = 400.dp, height = 50.dp),
                        content = {
                            Text(R.strings.componentPage, fontSize = 24.sp)
                        }
                    )
                }
            }
        }
    }
}
