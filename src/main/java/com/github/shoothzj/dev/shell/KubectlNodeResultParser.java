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

import com.github.shoothzj.dev.module.shell.KubectlNodeResult;
import com.github.shoothzj.dev.module.shell.KubectlNodeStatusEnum;
import com.github.shoothzj.dev.util.StringTool;

import java.util.ArrayList;
import java.util.List;

public class KubectlNodeResultParser {

    public static List<KubectlNodeResult> parseFull(List<String> body) throws Exception {
        return parseBody(body.subList(1, body.size()));
    }

    public static List<KubectlNodeResult> parseBody(List<String> body) throws Exception {
        List<KubectlNodeResult> list = new ArrayList<>();
        for (String s : body) {
            String[] fields = StringTool.fields(s);
            KubectlNodeResult kubectlNodeResult = new KubectlNodeResult();
            kubectlNodeResult.setName(fields[0]);
            switch (fields[1]) {
                case "Ready" -> kubectlNodeResult.setStatus(KubectlNodeStatusEnum.Ready);
                default -> kubectlNodeResult.setStatus(KubectlNodeStatusEnum.Unknown);
            }
            kubectlNodeResult.setRoles(fields[2]);
            kubectlNodeResult.setAge(fields[3]);
            kubectlNodeResult.setVersion(fields[4]);
            kubectlNodeResult.setInternalIp(fields[5]);
            kubectlNodeResult.setExternalIp(fields[6]);
            kubectlNodeResult.setOsImage(fields[7]);
            kubectlNodeResult.setKernelVersion(fields[8]);
            kubectlNodeResult.setContainerRuntime(fields[9]);
            list.add(kubectlNodeResult);
        }
        return list;
    }
}
