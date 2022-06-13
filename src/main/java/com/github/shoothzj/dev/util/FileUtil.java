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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    public static void ensureFileExists(String path) {
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e) {
            log.error("create file failed, exception is ", e);
        }
    }

    public static void openDirectory(String path) throws IOException {
        ProcessBuilder pb = new ProcessBuilder();
        ArrayList<String> commands = new ArrayList<>();
        commands.add("explorer.exe");
        commands.add(path);
        pb.command(commands);
        pb.start();
    }

}
