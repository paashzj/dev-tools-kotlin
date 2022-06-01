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

import com.github.shoothzj.dev.module.shell.ExecPodPsResult;
import com.github.shoothzj.dev.util.StringTool;

import java.util.ArrayList;
import java.util.List;

public class ExecPodPsResultParser {
    public static List<ExecPodPsResult> parseFull(List<String> input) {
      return parseBody(input.subList(1, input.size()));
    }

    public static List<ExecPodPsResult> parseBody(List<String> input) {
        List<ExecPodPsResult> execPodPsResults = new ArrayList<>();
        for (String s : input) {
            String[] fields = StringTool.fields(s);
            ExecPodPsResult podPsResult = new ExecPodPsResult();
            podPsResult.setUid(fields[0]);
            podPsResult.setPid(fields[1]);
            podPsResult.setPpid(fields[2]);
            podPsResult.setC(fields[3]);
            podPsResult.setStime(fields[4]);
            podPsResult.setTty(fields[5]);
            podPsResult.setTime(fields[6]);
            podPsResult.setCmd(fields[7]);
            execPodPsResults.add(podPsResult);
        }
        return execPodPsResults;
    }
}
