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
    val add: String
    val addKubernetesInstance: String
    val addNginxInstance: String
    val Commands: String
    val commandPage: String
    val componentList: String
    val config: String
    val confirm: String
    val content: String
    val convertPage: String
    val decode: String
    val delete: String
    val deployName: String
    val devTools: String
    val dismiss: String
    val encode: String
    val homePage: String
    val instance: String
    val kubernetesNodeName: String
    val match: String
    val name: String
    val namespace: String
    val notMatch: String
    val Params: String
    val receive: String
    val regex: String
    val regexExpression: String
    val SimulatorList: String
    val sceneList: String
    val statefulSetName: String
    val send: String
    val simulatorPage: String
    val TroubleComponent: String
    val TroubleSceneKubernetesNodeNotReady: String
    val TroubleSceneNginxHttpRequestFail: String
    val TroubleSceneZooKeeperClusterInitFail: String
    val troubleShootPage: String
    val troubleShootResult: String
    val VerifyList: String
    val verifyPage: String
}

object EnStrings : Strings {
    override val AppDescMessage: String
        get() = "↖️To explore tools, click the drawer"
    override val AppWelcomeMessage: String
        get() = "Welcome to Tool List, This is Home&Dashboard Page"
    override val aboutAuthor: String
        get() = "about author"
    override val add: String
        get() = "add"
    override val addKubernetesInstance: String
        get() = "add kubernetes instance"
    override val addNginxInstance: String
        get() = "add nginx instance"
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
    override val content: String
        get() = "content"
    override val convertPage: String
        get() = "convert page"
    override val decode: String
        get() = "decode"
    override val delete: String
        get() = "delete"
    override val deployName: String
        get() = "deploy name"
    override val devTools: String
        get() = "dev tools"
    override val dismiss: String
        get() = "dismiss"
    override val encode: String
        get() = "encode"
    override val homePage: String
        get() = "home page"
    override val instance: String
        get() = "instance"
    override val kubernetesNodeName: String
        get() = "kubernetes node name"
    override val match: String
        get() = "match"
    override val name: String
        get() = "name"
    override val namespace: String
        get() = "namespace"
    override val notMatch: String
        get() = "not match"
    override val Params: String
        get() = "Params"
    override val receive: String
        get() = "receive"
    override val regex: String
        get() = "regex"
    override val regexExpression: String
        get() = "regex repression"
    override val SimulatorList: String
        get() = "Simulator List"
    override val sceneList: String
        get() = "scene list"
    override val statefulSetName: String
        get() = "stateful set name"
    override val send: String
        get() = "send"
    override val simulatorPage: String
        get() = "simulator page"
    override val TroubleComponent: String
        get() = "Trouble Component"
    override val TroubleSceneKubernetesNodeNotReady: String
        get() = "Kubernetes node not ready"
    override val TroubleSceneNginxHttpRequestFail: String
        get() = "Nginx Http Request Fail"
    override val TroubleSceneZooKeeperClusterInitFail: String
        get() = "ZooKeeper Cluster Init Fail"
    override val troubleShootPage: String
        get() = "trouble shoot page"
    override val troubleShootResult: String
        get() = "trouble shoot result"
    override val VerifyList: String
        get() = "Verify List"
    override val verifyPage: String
        get() = "verify page"
}

object ChStrings : Strings {
    override val AppDescMessage: String
        get() = "↖️探索更多工具，请点击侧边栏"
    override val AppWelcomeMessage: String
        get() = "欢迎来到开发者工具集の主页面&dashboard界面"
    override val aboutAuthor: String
        get() = "关于作者"
    override val add: String
        get() = "添加"
    override val addKubernetesInstance: String
        get() = "添加kubernetes实例"
    override val addNginxInstance: String
        get() = "添加Nginx实例"
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
    override val content: String
        get() = "内容"
    override val convertPage: String
        get() = "转换界面"
    override val decode: String
        get() = "解码"
    override val delete: String
        get() = "删除"
    override val deployName: String
        get() = "部署名"
    override val devTools: String
        get() = "开发者工具集"
    override val dismiss: String
        get() = "取消"
    override val encode: String
        get() = "编码"
    override val homePage: String
        get() = "主界面"
    override val instance: String
        get() = "实例"
    override val kubernetesNodeName: String
        get() = "kubernetes 节点名"
    override val match: String
        get() = "匹配"
    override val name: String
        get() = "名称"
    override val namespace: String
        get() = "命名空间"
    override val notMatch: String
        get() = "不匹配"
    override val Params: String
        get() = "参数"
    override val receive: String
        get() = "接受"
    override val regex: String
        get() = "正则"
    override val regexExpression: String
        get() = "正则表达式"
    override val SimulatorList: String
        get() = "模拟器列表"
    override val sceneList: String
        get() = "场景列表"
    override val statefulSetName: String
        get() = "状态实例名"
    override val send: String
        get() = "发送"
    override val simulatorPage: String
        get() = "模拟器界面"
    override val TroubleComponent: String
        get() = "问题定位组件"
    override val TroubleSceneKubernetesNodeNotReady: String
        get() = "Kubernetes 节点 Not Ready"
    override val TroubleSceneNginxHttpRequestFail: String
        get() = "Nginx http请求失败"
    override val TroubleSceneZooKeeperClusterInitFail: String
        get() = "ZooKeeper 集群初始化失败"
    override val troubleShootPage: String
        get() = "问题定位界面"
    override val troubleShootResult: String
        get() = "问题定位结论"
    override val VerifyList: String
        get() = "验证列表"
    override val verifyPage: String
        get() = "验证界面"
}
