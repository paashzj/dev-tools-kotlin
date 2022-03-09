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

package com.github.shoothzj.dev.shell;

import com.github.shoothzj.dev.module.shell.NetstatResult;
import com.github.shoothzj.dev.util.StringTool;

import java.util.ArrayList;
import java.util.List;

public class NetstatResultParser {

    public static List<NetstatResult> parse(List<String> body) {
        List<NetstatResult> netstatResults = new ArrayList<>();
        for (String s : body) {
            String[] fields = StringTool.fields(s);
            if (fields.length < 7) {
                continue;
            }
            NetstatResult netstatResult = new NetstatResult();
            netstatResult.setProto(fields[0]);
            netstatResult.setRecvQ(fields[1]);
            netstatResult.setSendQ(fields[2]);
            netstatResult.setLocalAddress(fields[3]);
            netstatResult.setForeignAddress(fields[4]);
            if (!"udp".equals(fields[0])) {
                netstatResult.setState(fields[5]);
            }
            netstatResults.add(netstatResult);
        }
        return netstatResults;
    }

}
