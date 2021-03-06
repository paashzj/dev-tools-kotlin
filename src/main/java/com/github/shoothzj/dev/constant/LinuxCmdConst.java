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

package com.github.shoothzj.dev.constant;

import org.jetbrains.annotations.NotNull;

public class LinuxCmdConst {

    private static final int DEFAULT_TEXT_NUM = 50;

    public static final String CAT = "cat %s";

    public static final String IFCONFIG = "ifconfig";

    public static final String FREE_MEMORY = "free -m";

    public static final String SSH = "ssh -o StrictHostKeyChecking=no %s";

    public static final String GREP = "| grep %s";

    public static final String PORT_LISTEN = "netstat -lnp";

    public static final String HEAD = "| head -n %d";

    public static final String TAIL = "| tail -n %d";

    public static final String SYSCTL_PARAM = "sysctl -a";

    public static final String SCP_TO_NODE_CMD = "scp -o StrictHostKeyChecking=no %s %s:%s";

    public static final String SED_I_S_G = " sed -i 's/%s/%s/g' %s";

    @NotNull
    public static String path(@NotNull String... words) {
        return String.join(LinuxConst.PATH_SEP, words);
    }

    @NotNull
    public static String cat(@NotNull String str) {
        return String.format(CAT, str);
    }

    @NotNull
    public static String grep(@NotNull String... words) {
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(String.format(LinuxCmdConst.GREP, word));
        }
        return sb.toString();
    }

    @NotNull
    public static String head() {
        return head(DEFAULT_TEXT_NUM);
    }

    @NotNull
    public static String head(int num) {
        return String.format(HEAD, num);
    }

    @NotNull
    public static String tail() {
        return tail(DEFAULT_TEXT_NUM);
    }

    @NotNull
    public static String tail(int num) {
        return String.format(TAIL, num);
    }

    @NotNull
    public static String scpCmd(String resource, String host, String target) {
        return String.format(SCP_TO_NODE_CMD, resource, host, target);
    }

    @NotNull
    public static String lsCmd(String filePath) {
        return String.format("ls %s", filePath);
    }

    @NotNull
    public static String sedCmd(String resource, String target, String filename) {
        return String.format(SED_I_S_G, resource, target, filename);
    }

}
