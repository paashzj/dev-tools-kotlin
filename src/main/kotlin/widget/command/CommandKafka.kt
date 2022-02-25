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
fun CommandKafka() {
    val params = arrayOf(
        CommandParam("addr", "localhost:9092"),
        CommandParam("topic", "topic"),
        CommandParam("groupId", "group"),
    )
    val subcommandsList = arrayOf(
        SubCommands(
            "topic",
            arrayOf(
                Command(
                    "query topic",
                    "./kafka-topics.sh --bootstrap-server ${'$'}addr --list"
                ),
                Command(
                    "create topic",
                    "./kafka-topics.sh --bootstrap-server ${'$'}addr --create topic ${'$'}topic"
                ),
                Command(
                    "create topic with partition and replication factor",
                    "./kafka-topics.sh --bootstrap-server ${'$'}addr --create topic ${'$'}topic" +
                        " --partition 4 --replication-factor 1"
                ),
            )
        ),
        SubCommands(
            "produce",
            arrayOf(
                Command(
                    "produce message",
                    "./kafka-console-producer.sh  --bootstrap-server ${'$'}addr --topic ${'$'}topic"
                )
            )
        ),
        SubCommands(
            "consume",
            arrayOf(
                Command(
                    "consume message",
                    "./kafka-console-consumer.sh  --bootstrap-server ${'$'}addr --topic ${'$'}topic"
                ),
                Command(
                    "consume message from beginning",
                    "./kafka-console-consumer.sh  --bootstrap-server ${'$'}addr --from-beginning --topic ${'$'}topic"
                ),
                Command(
                    "consume message specify groupId",
                    "./kafka-console-consumer.sh  --consumer-property group.id=${'$'}groupId --bootstrap-server ${'$'}addr --topic ${'$'}topic"
                )
            )
        ),
    )
    val commandDto = CommandDto(subcommandsList)
    CommandView(params, commandDto)
}
