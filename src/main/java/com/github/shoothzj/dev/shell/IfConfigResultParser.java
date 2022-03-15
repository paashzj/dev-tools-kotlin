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

import com.github.shoothzj.dev.module.shell.IfConfigResult;
import com.github.shoothzj.dev.util.StringTool;
import com.github.shoothzj.dev.util.ValidateUtil;

import java.util.HashMap;
import java.util.List;

public class IfConfigResultParser {

    public static IfConfigResult parse(List<String> input) throws Exception {
        IfConfigResult ifConfigResult = new IfConfigResult();
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < input.size(); i++) {
            String aux = input.get(i);
            if (StringTool.leadingBlank(aux) == 0 && aux.startsWith("eth")) {
                String[] infFields = StringTool.fields(input.get(i));
                String[] ipFields = StringTool.fields(input.get(i + 1));
                if (ipFields.length > 5 && ValidateUtil.isHost(ipFields[2])) {
                    map.put(infFields[0].split(":")[0], ipFields[2]);
                }
            }
        }
        ifConfigResult.setIpMap(map);
        return ifConfigResult;
    }

}
