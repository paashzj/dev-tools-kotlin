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

package widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import module.NavigationEnum

@Composable
fun DrawContent(idx: MutableState<NavigationEnum>, scope: CoroutineScope, scaffoldState: ScaffoldState) {
    Column {
        Image(
            painter = painterResource("png/drawer_head.png"),
            contentDescription = "drawer head",
            modifier = Modifier
                .width(400.dp)
                .height(200.dp)
                .padding(10.dp),
        )
        TextButton(
            onClick = {
                idx.value = NavigationEnum.Home
                closeDrawer(scope, scaffoldState)
            },
            modifier = Modifier.size(width = 400.dp, height = 50.dp),
            content = {
                Text(R.strings.homePage)
            }
        )
        TextButton(
            onClick = {
                idx.value = NavigationEnum.Command
                closeDrawer(scope, scaffoldState)
            },
            modifier = Modifier.size(width = 400.dp, height = 50.dp),
            content = {
                Text(R.strings.commandPage)
            }
        )
        TextButton(
            onClick = {
                idx.value = NavigationEnum.Convert
                closeDrawer(scope, scaffoldState)
            },
            modifier = Modifier.size(width = 400.dp, height = 50.dp),
            content = {
                Text(R.strings.convertPage)
            }
        )
        TextButton(
            onClick = {
                idx.value = NavigationEnum.Simulator
                closeDrawer(scope, scaffoldState)
            },
            modifier = Modifier.size(width = 400.dp, height = 50.dp),
            content = {
                Text(R.strings.simulatorPage)
            }
        )
        TextButton(
            onClick = {
                idx.value = NavigationEnum.Component
                closeDrawer(scope, scaffoldState)
            },
            modifier = Modifier.size(width = 400.dp, height = 50.dp),
            content = {
                Text(R.strings.componentPage)
            }
        )
        TextButton(
            onClick = {
                idx.value = NavigationEnum.TroubleShoot
                closeDrawer(scope, scaffoldState)
            },
            modifier = Modifier.size(width = 400.dp, height = 50.dp),
            content = {
                Text(R.strings.troubleShootPage)
            }
        )
        TextButton(
            onClick = {
                idx.value = NavigationEnum.Verify
                closeDrawer(scope, scaffoldState)
            },
            modifier = Modifier.size(width = 400.dp, height = 50.dp),
            content = {
                Text(R.strings.verifyPage)
            }
        )
        TextButton(
            onClick = {
                idx.value = NavigationEnum.AboutAuthor
                closeDrawer(scope, scaffoldState)
            },
            modifier = Modifier.size(width = 400.dp, height = 50.dp)
        ) {
            Text(R.strings.aboutAuthor)
        }
        TextButton(
            onClick = {
                idx.value = NavigationEnum.Settings
                closeDrawer(scope, scaffoldState)
            },
            modifier = Modifier.size(width = 400.dp, height = 50.dp),
            content = {
                Text(R.strings.setting)
            }
        )
    }
}

private fun closeDrawer(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState
) {
    scope.launch {
        scaffoldState.drawerState.close()
    }
}

@Composable
fun TopBar(scope: CoroutineScope, scaffoldState: ScaffoldState) {
    TopAppBar(
        title = { Text(text = R.strings.devTools, fontSize = 18.sp) },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(Icons.Filled.Menu, "")
            }
        },
        backgroundColor = Color.Blue,
        contentColor = Color.White
    )
}
