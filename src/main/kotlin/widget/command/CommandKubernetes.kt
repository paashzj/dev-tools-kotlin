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

import androidx.compose.runtime.Composable
import com.github.shoothzj.dev.constant.K8sCmdConst
import module.Command
import module.CommandDto
import module.CommandParam
import module.SubCommands

@Composable
fun CommandKubernetes() {
    val params = arrayOf(
        CommandParam("namespace", "default"),
        CommandParam("nodeName", "node-name"),
        CommandParam("podName", "pod-name")
    )
    val subcommandsList = arrayOf(
        SubCommands(
            "node",
            arrayOf(
                Command(
                    "get node list",
                    K8sCmdConst.GET_NODE_LIST
                ),
                Command(
                    "describe node",
                    String.format(K8sCmdConst.DESCRIBE_NODE, "${'$'}nodeName"),
                ),
            )
        ),
        SubCommands(
            "statefulset",
            arrayOf(
                Command(
                    "get statefulset list",
                    String.format(K8sCmdConst.GET_STATEFUL_SET_LIST, "${'$'}namespace"),
                ),
            )
        ),
        SubCommands(
            "deploy",
            arrayOf(
                Command(
                    "get deploy list",
                    String.format(K8sCmdConst.GET_DEPLOY_LIST, "${'$'}namespace"),
                ),
            )
        ),
        SubCommands(
            "service",
            arrayOf(
                Command(
                    "get service list",
                    String.format(K8sCmdConst.GET_SERVICE_LIST, "${'$'}namespace"),
                ),
            )
        ),
        SubCommands(
            "pod",
            arrayOf(
                Command(
                    "get pod list",
                    String.format(K8sCmdConst.GET_POD_LIST, "${'$'}namespace"),
                ),
                Command(
                    "describe pod",
                    String.format(K8sCmdConst.DESCRIBE_POD, "${'$'}podName", "${'$'}namespace"),
                ),
            )
        ),
    )
    val commandDto = CommandDto(subcommandsList)
    CommandView(params, commandDto)
}
