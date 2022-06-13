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

package widget.kubernetes

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.rememberDialogState
import com.github.shoothzj.dev.module.UiResponse
import com.github.shoothzj.dev.state.State
import com.github.shoothzj.dev.util.FileUtil
import constant.PixelConst.Companion.configDialogSize

@Composable
fun PodsDetailResult(
    dialogState: MutableState<Boolean>,
    content: MutableState<UiResponse<String>>
): @Composable Unit {
    Column {
        if (dialogState.value) {
            if (content.value.code == State.OK.code) {
                FileUtil.openDirectory(content.value.contents[0])
                return
            }
            Dialog(
                onCloseRequest = {
                    dialogState.value = false
                },
                state = rememberDialogState(size = configDialogSize),
                title = R.strings.Prompt,
            ) {
                Text(content.value.reason)
            }
        }
    }
}
