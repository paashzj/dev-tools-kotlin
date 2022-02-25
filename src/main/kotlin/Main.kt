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

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import constat.PixelConst
import module.NavigationEnum
import widget.DrawContent
import widget.TopBar
import widget.command.CommandScreen
import widget.convert.ConvertScreen
import widget.general.AboutAuthorScreen
import widget.general.HomeScreen
import widget.simulator.SimulatorScreen

fun main() = application {
    val navigationIdx = mutableStateOf(NavigationEnum.Home)
    Window(
        onCloseRequest = ::exitApplication,
        title = "dev tools",
        state = rememberWindowState(size = PixelConst.appSize)
    ) {
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { TopBar(scope = coroutineScope, scaffoldState = scaffoldState) },
            drawerBackgroundColor = MaterialTheme.colors.background,
            drawerShape = drawerShape(),
            drawerContent = {
                DrawContent(navigationIdx, coroutineScope, scaffoldState)
            },
        ) {
            MaterialTheme {
                when (navigationIdx.value) {
                    NavigationEnum.Home -> {
                        HomeScreen()
                    }
                    NavigationEnum.Command -> {
                        CommandScreen()
                    }
                    NavigationEnum.Convert -> {
                        ConvertScreen()
                    }
                    NavigationEnum.Simulator -> {
                        SimulatorScreen()
                    }
                    NavigationEnum.AboutAuthor -> {
                        AboutAuthorScreen()
                    }
                }
            }
        }
    }
}

fun drawerShape() = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Rectangle(Rect(0f, 0f, size.width / 3, size.height))
    }
}
