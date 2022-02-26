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

import com.github.shoothzj.dev.module.config.BaseConfig;
import com.github.shoothzj.javatool.service.JacksonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class AbstractStorage<T extends BaseConfig> {
    
    protected abstract String getConfigPath();

    public boolean saveConfig(T config) {
        Map<String, T> map = getConfigMap();
        if (map.containsKey(config.getName())) {
            return false;
        }
        map.put(config.getName(), config);
        String json = JacksonService.toJson(map);
        try {
            FileUtils.writeStringToFile(new File(getConfigPath()), json, StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            log.error("save config name {} exception is ", config.getName(), e);
            return false;
        }
    }

    public boolean deleteConfig(String name) {
        Map<String, T> map = getConfigMap();
        map.remove(name);
        String json = JacksonService.toJson(map);
        try {
            FileUtils.writeStringToFile(new File(getConfigPath()), json, StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            log.error("delete config name {} exception is ", name, e);
            return false;
        }
    }

    public T getConfig(String name) {
        return getConfigMap().get(name);
    }

    public List<String> listContent() {
        return getConfigMap().values().stream().map(JacksonService::toJson).toList();
    }

    public List<String> listConfigNames() {
        return getConfigMap().keySet().stream().toList();
    }

    public Map<String, T> getConfigMap() {
        try {
            File file = new File(getConfigPath());
            String json = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            Map<String, T> result = deserialize(json);
            return result == null ? new HashMap<>() : result;
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    protected abstract Map<String, T> deserialize(String json);

}
