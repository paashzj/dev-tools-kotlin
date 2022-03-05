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

package constant

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize

class PixelConst {

    companion object {
        val widthDp = Dp(1400f)
        val heightDp = Dp(900f)
        val appSize = DpSize(widthDp, heightDp)
        val configDialogSize = DpSize(widthDp * 2 / 3, heightDp * 3 / 4)
        val configErrorDialogSize = DpSize(widthDp / 3, heightDp / 5)
    }
}
