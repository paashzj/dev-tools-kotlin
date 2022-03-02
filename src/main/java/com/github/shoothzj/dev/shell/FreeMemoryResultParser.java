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

import com.github.shoothzj.dev.module.shell.FreeMemoryResult;
import com.github.shoothzj.dev.util.StringTool;

import java.util.List;

public class FreeMemoryResultParser {

    public static FreeMemoryResult parse(List<String> input) {
        FreeMemoryResult freeMemoryResult = new FreeMemoryResult();
        if (input.size() < 3) {
            return freeMemoryResult;
        }
        String[] fields = StringTool.fields(input.get(1));
        freeMemoryResult.setMemTotalBytes(Long.parseLong(fields[1]));
        freeMemoryResult.setMemUsedBytes(Long.parseLong(fields[2]));
        freeMemoryResult.setMemFreeBytes(Long.parseLong(fields[3]));
        freeMemoryResult.setMemSharedBytes(Long.parseLong(fields[4]));
        freeMemoryResult.setMemBufferCacheBytes(Long.parseLong(fields[5]));
        freeMemoryResult.setMemAvailableBytes(Long.parseLong(fields[6]));
        return freeMemoryResult;
    }

}
