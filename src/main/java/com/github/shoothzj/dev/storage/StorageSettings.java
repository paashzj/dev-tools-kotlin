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

    private static final StorageSettings INSTANCE = new StorageSettings();

    private static final Settings DEFAULT_SETTINGS = new Settings();

    static {
        DEFAULT_SETTINGS.setSshExecuteTimeoutSeconds(20);
        DEFAULT_SETTINGS.setSshJumpTimeoutSeconds(5);
        DEFAULT_SETTINGS.setSshLoginTimeoutSeconds(5);

        StorageSettings config = new StorageSettings();
        File file = new File(config.getConfigPath());
        if (!file.exists()) {
            config.saveConfig(DEFAULT_SETTINGS);
        }
        log.info("init settings success.");
    }

    public static StorageSettings getInstance() {
        return INSTANCE;
    }

    protected String getConfigPath() {
        return StorageUtil.SETTING_PATH;
    }

    public boolean saveConfig(Settings config) {
        String json = JacksonService.toJson(config);
        try {
            FileUtils.writeStringToFile(new File(getConfigPath()), json, StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            log.error("save config exception is ", e);
            return false;
        }
    }

    public Settings getConfig() {
        try {
            String json = FileUtils.readFileToString(new File(getConfigPath()), StandardCharsets.UTF_8);
            return JacksonService.toObject(json, Settings.class);
        } catch (IOException e) {
            log.error("fail to get config.");
            return DEFAULT_SETTINGS;
        }
    }
}
