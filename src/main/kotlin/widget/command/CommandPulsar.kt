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
import module.Command
import module.CommandDto
import module.CommandParam
import module.SubCommands

@Composable
fun CommandPulsar() {
    val namespaceParam = "${'$'}tenant/${'$'}namespace"
    val topicParam = "${'$'}tenant/${'$'}namespace/${'$'}topic"
    val params = arrayOf(
        CommandParam("tenant", "public"),
        CommandParam("namespace", "default"),
        CommandParam("topic", "topic"),
        CommandParam("subscription", "test"),
    )
    val subcommandsList = arrayOf(
        SubCommands(
            "broker",
            arrayOf(
                Command(
                    "health check",
                    "./pulsar-admin brokers healthcheck"
                ),
            )
        ),
        SubCommands(
            "namespace",
            arrayOf(
                Command(
                    "set retention",
                    "./bin/pulsar-admin namespaces set-retention --size 10M -t 2d $namespaceParam"
                ),
            )
        ),
        SubCommands(
            "topic",
            arrayOf(
                Command(
                    "topic list",
                    "./pulsar-admin topics $namespaceParam"
                ),
                Command(
                    "create partitioned topic",
                    "./pulsar-admin topics create-partitioned-topic $namespaceParam/${'$'}topic -p 4"
                ),
                Command(
                    "partitioned-stats",
                    "./pulsar-admin topics partitioned-stats $topicParam"
                ),
                Command(
                    "stats",
                    "./pulsar-admin topics stats $topicParam"
                ),
            )
        ),
        SubCommands(
            "produce",
            arrayOf(
                Command(
                    "produce message",
                    "./pulsar-client produce persistent://$namespaceParam/${'$'}topic -m \"content\""
                )
            )
        ),
        SubCommands(
            "consume",
            arrayOf(
                Command(
                    "consume message",
                    "./pulsar-client consume $topicParam -s ${'$'}subscription"
                ),
            )
        ),
    )
    val commandDto = CommandDto(subcommandsList)
    CommandView(params, commandDto)
}
