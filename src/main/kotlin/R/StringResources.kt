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

package R

import java.util.Locale

val strings = when (Locale.getDefault().language) {
    "en" -> EnStrings
    "zh" -> ChStrings
    else -> EnStrings
}

interface Strings {
    val AppDescMessage: String
    val AppWelcomeMessage: String
    val aboutAuthor: String
    val Commands: String
    val commandPage: String
    val componentList: String
    val config: String
    val confirm: String
    val convertPage: String
    val decode: String
    val devTools: String
    val dismiss: String
    val encode: String
    val homePage: String
    val Params: String
    val receive: String
    val SimulatorList: String
    val send: String
    val simulatorPage: String
    val TroubleScene: String
    val troubleShootPage: String
}

object EnStrings : Strings {
    override val AppDescMessage: String
        get() = "To explore tools, click the drawer"
    override val AppWelcomeMessage: String
        get() = "Welcome to Tool List, This is Home&Dashboard Page"
    override val aboutAuthor: String
        get() = "about author"
    override val Commands: String
        get() = "Commands"
    override val commandPage: String
        get() = "command page"
    override val componentList: String
        get() = "component list"
    override val config: String
        get() = "config"
    override val confirm: String
        get() = "confirm"
    override val convertPage: String
        get() = "convert page"
    override val decode: String
        get() = "decode"
    override val devTools: String
        get() = "dev tools"
    override val dismiss: String
        get() = "dismiss"
    override val encode: String
        get() = "encode"
    override val homePage: String
        get() = "home page"
    override val Params: String
        get() = "Params"
    override val receive: String
        get() = "receive"
    override val SimulatorList: String
        get() = "Simulator List"
    override val send: String
        get() = "send"
    override val simulatorPage: String
        get() = "simulator page"
    override val TroubleScene: String
        get() = "Trouble Scene"
    override val troubleShootPage: String
        get() = "trouble shoot page"
}

object ChStrings : Strings {
    override val AppDescMessage: String
        get() = "发现更多工具，请点击侧边栏"
    override val AppWelcomeMessage: String
        get() = "欢迎来到开发者工具集，此页面为主页面&dashboard界面"
    override val aboutAuthor: String
        get() = "关于作者"
    override val Commands: String
        get() = "命令"
    override val commandPage: String
        get() = "命令界面"
    override val componentList: String
        get() = "组件列表"
    override val config: String
        get() = "配置"
    override val confirm: String
        get() = "确认"
    override val convertPage: String
        get() = "转换界面"
    override val decode: String
        get() = "解码"
    override val devTools: String
        get() = "开发者工具集"
    override val dismiss: String
        get() = "取消"
    override val encode: String
        get() = "编码"
    override val homePage: String
        get() = "主界面"
    override val Params: String
        get() = "参数"
    override val receive: String
        get() = "接受"
    override val SimulatorList: String
        get() = "模拟器列表"
    override val send: String
        get() = "发送"
    override val simulatorPage: String
        get() = "模拟器界面"
    override val TroubleScene: String
        get() = "问题定位场景"
    override val troubleShootPage: String
        get() = "问题定位界面"
}
