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

package widget.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChapterElement(
    title: String,
    content: @Composable () -> Unit,
    isDefaultShowContent: Boolean = true,
) {
    var showContent by remember { mutableStateOf(isDefaultShowContent) }
    val icon: ImageVector = if (showContent) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.ArrowDropDown
    }

    Column(modifier = Modifier.padding(bottom = 2.dp, top = 2.dp)) {
        Row {
            Icon(
                icon, "contentDescription",
                Modifier.clickable { showContent = !showContent }
            )
            Text(title, fontSize = 30.sp)
        }
        if (showContent) {
            content()
        }
    }
}
