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

import com.github.shoothzj.dev.module.shell.KubectlDeployResult;
import com.github.shoothzj.dev.util.StringTool;

import java.util.ArrayList;
import java.util.List;

public class KubectlDeployResultParser {

    public static List<KubectlDeployResult> parseFull(List<String> body) throws Exception {
        return parseBody(body.subList(1, body.size()));
    }

    public static List<KubectlDeployResult> parseBody(List<String> body) throws Exception {
        List<KubectlDeployResult> list = new ArrayList<>();
        for (String s : body) {
            String[] fields = StringTool.fields(s);
            KubectlDeployResult kubectlDeployResult = new KubectlDeployResult();
            kubectlDeployResult.setName(fields[0]);
            kubectlDeployResult.setReady(fields[1]);
            kubectlDeployResult.setUpToDate(fields[2]);
            kubectlDeployResult.setAvailable(fields[3]);
            kubectlDeployResult.setAge(fields[4]);
            kubectlDeployResult.setContainers(fields[5]);
            kubectlDeployResult.setImages(fields[6]);
            kubectlDeployResult.setSelector(fields[7]);
            list.add(kubectlDeployResult);
        }
        return list;
    }

}
