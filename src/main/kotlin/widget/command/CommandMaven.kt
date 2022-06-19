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
import module.SubCommands

@Composable
fun CommandMaven() {
    val subcommandsList = arrayOf(
        SubCommands(
            "checkstyle",
            arrayOf(
                Command(
                    "checkstyle",
                    "mvn checkstyle:check"
                ),
            )
        ),
        SubCommands(
            "proxy",
            arrayOf(
                Command(
                    "proxy",
                    """export MAVEN_OPTS="-DsocksProxyHost=127.0.0.1 -DsocksProxyPort=1080""""
                ),
            )
        ),
    )
    val commandDto = CommandDto(subcommandsList)
    CommandViewStatic(commandDto)
}
