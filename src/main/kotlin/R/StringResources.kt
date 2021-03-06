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
    val AutoConsume: String
    val aboutAuthor: String
    val add: String
    val addKubernetesInstance: String
    val addNginxInstance: String
    val back: String
    val backHome: String
    val broadcast: String
    val Commands: String
    val close: String
    val commandPage: String
    val componentPage: String
    val componentList: String
    val config: String
    val confirm: String
    val connect: String
    val containerIp: String
    val content: String
    val convert: String
    val convertPage: String
    val decode: String
    val details: String
    val delete: String
    val deployName: String
    val devTools: String
    val dismiss: String
    val encode: String
    val enter: String
    val execute: String
    val generate: String
    val generatePath: String
    val homePage: String
    val httpCodes: String
    val httpMethods: String
    val httpRequestUrl: String
    val imageVersion: String
    val installTcpdumpFile: String
    val instance: String
    val kubernetesNodeName: String
    val localTransfer: String
    val ManualConsume: String
    val match: String
    val msgList: String
    val name: String
    val namespace: String
    val nodeIp: String
    val notMatch: String
    val Params: String
    val Prompt: String
    val pulsarBrokerHost: String
    val pulsarBrokerPort: String
    val pulsarFunctionHost: String
    val pulsarFunctionPort: String
    val receive: String
    val receiveMsg: String
    val regex: String
    val regexExpression: String
    val replace: String
    val SimulatorList: String
    val sceneList: String
    val send: String
    val setting: String
    val simulatorPage: String
    val startTime: String
    val statefulSetName: String
    val subscribe: String
    val Transfer: String
    val TroubleComponent: String
    val TroubleSceneClusterInitFail: String
    val TroubleSceneConnFail: String
    val TroubleSceneKubernetesNodeNotReady: String
    val TroubleSceneLvsVirtualIpDetect: String
    val TroubleSceneMysqlSlowSql: String
    val TroubleSceneNginxHttpRequestFail: String
    val TroubleSceneZooKeeperClusterInitFail: String
    val troubleShootPage: String
    val troubleShootResult: String
    val VerifyList: String
    val verifyPage: String
}

object EnStrings : Strings {
    override val AppDescMessage: String
        get() = "??????To explore tools, click the drawer"
    override val AppWelcomeMessage: String
        get() = "Welcome to Tool List, This is Home&Dashboard Page"
    override val AutoConsume: String
        get() = "Auto Consume"
    override val aboutAuthor: String
        get() = "about author"
    override val add: String
        get() = "add"
    override val addKubernetesInstance: String
        get() = "add kubernetes instance"
    override val addNginxInstance: String
        get() = "add nginx instance"
    override val back: String
        get() = "back"
    override val backHome: String
        get() = "back home"
    override val broadcast: String
        get() = "broadcast"
    override val Commands: String
        get() = "Commands"
    override val close: String
        get() = "close"
    override val commandPage: String
        get() = "command page"
    override val componentPage: String
        get() = "component page"
    override val componentList: String
        get() = "component list"
    override val config: String
        get() = "config"
    override val confirm: String
        get() = "confirm"
    override val connect: String
        get() = "connect"
    override val containerIp: String
        get() = "container Ip"
    override val content: String
        get() = "content"
    override val convert: String
        get() = "convert"
    override val convertPage: String
        get() = "convert page"
    override val decode: String
        get() = "decode"
    override val details: String
        get() = "details list"
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
    override val enter: String
        get() = "enter"
    override val execute: String
        get() = "execute"
    override val generate: String
        get() = "generate"
    override val generatePath: String
        get() = "generate path"
    override val homePage: String
        get() = "home page"
    override val httpCodes: String
        get() = "http codes"
    override val httpMethods: String
        get() = "http methods"
    override val httpRequestUrl: String
        get() = "http request url"
    override val imageVersion: String
        get() = "image version"
    override val installTcpdumpFile: String
        get() = "install Tcpdump"
    override val instance: String
        get() = "instance"
    override val kubernetesNodeName: String
        get() = "kubernetes node name"
    override val localTransfer: String
        get() = "local transfer"
    override val ManualConsume: String
        get() = "manual consume"
    override val match: String
        get() = "match"
    override val msgList: String
        get() = "msg list"
    override val name: String
        get() = "name"
    override val namespace: String
        get() = "namespace"
    override val nodeIp: String
        get() = "node Ip"
    override val notMatch: String
        get() = "not match"
    override val Params: String
        get() = "Params"
    override val Prompt: String
        get() = "prompt"
    override val pulsarBrokerHost: String
        get() = "pulsar broker host"
    override val pulsarBrokerPort: String
        get() = "pulsar broker port"
    override val pulsarFunctionHost: String
        get() = "pulsar function host"
    override val pulsarFunctionPort: String
        get() = "pulsar function port"
    override val receive: String
        get() = "receive"
    override val receiveMsg: String
        get() = "receive msg"
    override val regex: String
        get() = "regex"
    override val regexExpression: String
        get() = "regex repression"
    override val replace: String
        get() = "replace"
    override val SimulatorList: String
        get() = "Simulator List"
    override val sceneList: String
        get() = "scene list"
    override val statefulSetName: String
        get() = "stateful set name"
    override val setting: String
        get() = "setting"
    override val send: String
        get() = "send"
    override val simulatorPage: String
        get() = "simulator page"
    override val startTime: String
        get() = "start time"
    override val subscribe: String
        get() = "subscribe"
    override val Transfer: String
        get() = "transfer"
    override val TroubleComponent: String
        get() = "Trouble Component"
    override val TroubleSceneClusterInitFail: String
        get() = "Cluster Init Fail"
    override val TroubleSceneConnFail: String
        get() = "Conn Fail"
    override val TroubleSceneKubernetesNodeNotReady: String
        get() = "Kubernetes node not ready"
    override val TroubleSceneLvsVirtualIpDetect: String
        get() = "Lvs Virtual Ip detect"
    override val TroubleSceneMysqlSlowSql: String
        get() = "Mysql slow sql"
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
        get() = "?????????????????????????????????????????????"
    override val AppWelcomeMessage: String
        get() = "??????????????????????????????????????????&dashboard??????"
    override val AutoConsume: String
        get() = "????????????"
    override val aboutAuthor: String
        get() = "????????????"
    override val add: String
        get() = "??????"
    override val addKubernetesInstance: String
        get() = "??????kubernetes??????"
    override val addNginxInstance: String
        get() = "??????Nginx??????"
    override val back: String
        get() = "??????"
    override val backHome: String
        get() = "????????????"
    override val broadcast: String
        get() = "??????"
    override val Commands: String
        get() = "??????"
    override val close: String
        get() = "??????"
    override val commandPage: String
        get() = "????????????"
    override val componentList: String
        get() = "????????????"
    override val componentPage: String
        get() = "????????????"
    override val config: String
        get() = "??????"
    override val confirm: String
        get() = "??????"
    override val connect: String
        get() = "??????"
    override val containerIp: String
        get() = "??????Ip"
    override val content: String
        get() = "??????"
    override val convert: String
        get() = "??????"
    override val convertPage: String
        get() = "????????????"
    override val decode: String
        get() = "??????"
    override val details: String
        get() = "????????????"
    override val delete: String
        get() = "??????"
    override val deployName: String
        get() = "?????????"
    override val devTools: String
        get() = "??????????????????"
    override val dismiss: String
        get() = "??????"
    override val encode: String
        get() = "??????"
    override val enter: String
        get() = "??????"
    override val execute: String
        get() = "??????"
    override val generate: String
        get() = "??????"
    override val generatePath: String
        get() = "????????????"
    override val homePage: String
        get() = "?????????"
    override val httpCodes: String
        get() = "http ?????????"
    override val httpMethods: String
        get() = "http ??????"
    override val httpRequestUrl: String
        get() = "http ?????? url"
    override val imageVersion: String
        get() = "????????????"
    override val installTcpdumpFile: String
        get() = "??????Tcpdump"
    override val instance: String
        get() = "??????"
    override val kubernetesNodeName: String
        get() = "kubernetes ?????????"
    override val localTransfer: String
        get() = "????????????"
    override val ManualConsume: String
        get() = "????????????"
    override val match: String
        get() = "??????"
    override val msgList: String
        get() = "????????????"
    override val name: String
        get() = "??????"
    override val namespace: String
        get() = "????????????"
    override val nodeIp: String
        get() = "?????? Ip"
    override val notMatch: String
        get() = "?????????"
    override val Params: String
        get() = "??????"
    override val Prompt: String
        get() = "??????"
    override val pulsarBrokerHost: String
        get() = "pulsar broker host"
    override val pulsarBrokerPort: String
        get() = "pulsar broker ??????"
    override val pulsarFunctionHost: String
        get() = "pulsar function host"
    override val pulsarFunctionPort: String
        get() = "pulsar function ??????"
    override val receive: String
        get() = "??????"
    override val receiveMsg: String
        get() = "????????????"
    override val regex: String
        get() = "??????"
    override val regexExpression: String
        get() = "???????????????"
    override val replace: String
        get() = "??????"
    override val SimulatorList: String
        get() = "???????????????"
    override val sceneList: String
        get() = "????????????"
    override val setting: String
        get() = "??????"
    override val send: String
        get() = "??????"
    override val simulatorPage: String
        get() = "???????????????"
    override val startTime: String
        get() = "????????????"
    override val statefulSetName: String
        get() = "???????????????"
    override val subscribe: String
        get() = "??????"
    override val Transfer: String
        get() = "??????"
    override val TroubleComponent: String
        get() = "??????????????????"
    override val TroubleSceneClusterInitFail: String
        get() = "?????????????????????"
    override val TroubleSceneConnFail: String
        get() = "????????????"
    override val TroubleSceneKubernetesNodeNotReady: String
        get() = "Kubernetes ?????? Not Ready"
    override val TroubleSceneLvsVirtualIpDetect: String
        get() = "Lvs ??????Ip??????"
    override val TroubleSceneMysqlSlowSql: String
        get() = "Mysql ???sql"
    override val TroubleSceneNginxHttpRequestFail: String
        get() = "Nginx http????????????"
    override val TroubleSceneZooKeeperClusterInitFail: String
        get() = "ZooKeeper ?????????????????????"
    override val troubleShootPage: String
        get() = "??????????????????"
    override val troubleShootResult: String
        get() = "??????????????????"
    override val VerifyList: String
        get() = "????????????"
    override val verifyPage: String
        get() = "????????????"
}
