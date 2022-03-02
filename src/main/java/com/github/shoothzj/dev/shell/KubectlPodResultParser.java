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

import com.github.shoothzj.dev.module.shell.KubectlPodResult;
import com.github.shoothzj.dev.module.shell.KubectlPodStatusEnum;
import com.github.shoothzj.dev.util.StringTool;

import java.util.ArrayList;
import java.util.List;

public class KubectlPodResultParser {

    public static List<KubectlPodResult> parseFull(List<String> body) throws Exception {
        return parseBody(body.subList(1, body.size()));
    }

    public static List<KubectlPodResult> parseBody(List<String> body) throws Exception {
        List<KubectlPodResult> list = new ArrayList<>();
        for (String s : body) {
            String[] fields = StringTool.fields(s);
            KubectlPodResult kubectlPodResult = new KubectlPodResult();
            kubectlPodResult.setPodName(fields[0]);
            kubectlPodResult.setReady(fields[1].equals("1/1"));
            if (fields[2].equals("ImagePullBackOff")) {
                kubectlPodResult.setStatus(KubectlPodStatusEnum.ImagePullBackOff);
            } else if (fields[2].equals("Running")) {
                kubectlPodResult.setStatus(KubectlPodStatusEnum.Running);
            } else if (fields[2].equals("Terminating")) {
                kubectlPodResult.setStatus(KubectlPodStatusEnum.Terminating);
            } else {
                kubectlPodResult.setStatus(KubectlPodStatusEnum.Unknown);
            }
            kubectlPodResult.setRestarts(Integer.parseInt(fields[3]));
            kubectlPodResult.setAge(fields[4]);
            kubectlPodResult.setIp(fields[5]);
            kubectlPodResult.setNode(fields[6]);
            list.add(kubectlPodResult);
        }
        return list;
    }
}
