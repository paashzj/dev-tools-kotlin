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

public class ShellUtil {

    /**
     * https://stackoverflow.com/questions/17998978/removing-colors-from-output
     * @param str
     * @return
     */
    public static String removeColor(String str) {
        str = str.replaceAll("(\\x1B\\[[\\d;]*[a-zA-Z]| \r)", "");
        return str.replaceAll("(.)\r\\1", "$1");
    }

}
