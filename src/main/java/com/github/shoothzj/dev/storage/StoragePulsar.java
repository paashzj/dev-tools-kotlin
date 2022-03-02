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

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.shoothzj.dev.module.config.PulsarConfig;
import com.github.shoothzj.javatool.service.JacksonService;

import java.util.Map;

public class StoragePulsar extends AbstractStorage<PulsarConfig> {

    private static final StoragePulsar INSTANCE = new StoragePulsar();

    public static StoragePulsar getInstance() {
        return INSTANCE;
    }

    @Override
    protected String getConfigPath() {
        return StorageUtil.PULSAR_CONF_PATH;
    }

    @Override
    protected Map<String, PulsarConfig> deserialize(String json) {
        return JacksonService.toRefer(json, new TypeReference<>() {
        });
    }
}
