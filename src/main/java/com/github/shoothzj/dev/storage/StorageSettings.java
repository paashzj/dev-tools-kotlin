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

package com.github.shoothzj.dev.storage;

import com.github.shoothzj.dev.module.config.Settings;
import com.github.shoothzj.javatool.service.JacksonService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class StorageSettings {
    private static final Logger log = LoggerFactory.getLogger(StorageSettings.class);
    public static boolean saveConfig(Settings config) {
        String json = JacksonService.toJson(config);
        try {
            FileUtils.writeStringToFile(new File(StorageUtil.SETTING_PATH), json, StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            log.error("save config exception is ", e);
            return false;
        }
    }

    public static Settings getConfig() {
        try {
            String json = FileUtils.readFileToString(new File(StorageUtil.SETTING_PATH), StandardCharsets.UTF_8);
            Settings settings = JacksonService.toObject(json, Settings.class);
            if (settings == null) {
                return new Settings();
            }
            return settings;
        } catch (IOException e) {
            log.error("fail to get config ", e);
            return new Settings();
        }
    }
}
