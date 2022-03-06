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

package widget.general

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import module.Author

val authors = arrayOf(
    Author("fu_turer", "Tian Luo", "futurer@outlook.com"),
    Author("goflutterjava", "KeLe He", "goflutterjava@gmail.com"),
    Author("Lico-Tom", "ZhongHao Li", "583566138@qq.com"),
    Author("lovehzj", "TingTing Wang", "1922919664@qq.com"),
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AboutAuthorScreen() {
    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        modifier = Modifier.padding(horizontal = 15.dp, vertical = 50.dp),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center
    ) {
        items(authors.size * 3 + 3) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (it < 3) {
                    if (it == 0) {
                        Text("github id")
                    }
                    if (it == 1) {
                        Text("name")
                    }
                    if (it == 2) {
                        Text("email")
                    }
                } else {
                    val i = it - 3
                    if (i % 3 == 0) {
                        Text(authors[i / 3].githubId)
                    }
                    if (i % 3 == 1) {
                        Text(authors[i / 3].name)
                    }
                    if (i % 3 == 2) {
                        Text(authors[i / 3].email)
                    }
                }
            }
        }
    }
}
