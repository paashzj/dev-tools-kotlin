/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.github.shoothzj.dev.util;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class StringTool {

    public static boolean anyLineMatch(List<String> result, @NotNull Pattern pattern) {
        return result.stream().anyMatch(s -> pattern.matcher(s).matches());
    }

    public static boolean anyLineMatch(@NotNull String result, @NotNull Pattern pattern) {
        return Arrays.stream(result.split("\\n")).anyMatch(s -> pattern.matcher(s).matches());
    }

    public static boolean anyLineContains(List<String> result, String str) {
        return result.stream().anyMatch(s -> s.contains(str));
    }

    @NotNull
    public static String[] fields(@NotNull String src) {
        return src.split("\\s+");
    }

    public static int leadingBlank(@NotNull String str) {
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] != ' ' && charArray[i] != '\t') {
                return i;
            }
        }
        return charArray.length;
    }

}
