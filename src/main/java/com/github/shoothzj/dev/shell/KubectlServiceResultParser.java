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

import com.github.shoothzj.dev.module.shell.KubectlServiceResult;
import com.github.shoothzj.dev.module.shell.KubectlServiceTypeEnum;
import com.github.shoothzj.dev.util.StringTool;

import java.util.ArrayList;
import java.util.List;

public class KubectlServiceResultParser {

    public static List<KubectlServiceResult> parseFull(List<String> body) throws Exception {
        return parseBody(body.subList(1, body.size()));
    }

    public static List<KubectlServiceResult> parseBody(List<String> body) throws Exception {
        List<KubectlServiceResult> list = new ArrayList<>();
        for (String s : body) {
            String[] fields = StringTool.fields(s);
            KubectlServiceResult kubectlServiceResult = new KubectlServiceResult();
            kubectlServiceResult.setName(fields[0]);
            switch (fields[1]) {
                case "ClusterIP" -> kubectlServiceResult.setType(KubectlServiceTypeEnum.ClusterIP);
                default -> kubectlServiceResult.setType(KubectlServiceTypeEnum.Unknown);
            }
            kubectlServiceResult.setClusterIp(fields[2]);
            kubectlServiceResult.setExternalIp(fields[3]);
            kubectlServiceResult.setPorts(fields[5]);
            kubectlServiceResult.setSelector(fields[6]);
            list.add(kubectlServiceResult);
        }
        return list;
    }

}
