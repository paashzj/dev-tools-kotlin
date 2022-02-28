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

import com.github.shoothzj.dev.util.FileUtil;

import java.io.File;

public class StorageUtil {

    public static final String USER_DIR = System.getProperty("user.home");

    public static final String APP_DIR = USER_DIR + File.separator + "dev-tools-kotlin";

    public static final String STORAGE_DIR = APP_DIR + File.separator + "storage";

    public static final String STORAGE_PREFIX = STORAGE_DIR + File.separator;

    public static final String K8S_CONF_PATH = STORAGE_PREFIX + "kubernetes-conf.json";

    public static final String NGX_CONF_PATH = STORAGE_PREFIX + "nginx-conf.json";

    public static final String ZK_CONF_PATH = STORAGE_PREFIX + "zookeeper-conf.json";

    static {
        File storageDir = new File(STORAGE_DIR);
        storageDir.mkdirs();
        FileUtil.ensureFileExists(K8S_CONF_PATH);
        FileUtil.ensureFileExists(NGX_CONF_PATH);
        FileUtil.ensureFileExists(ZK_CONF_PATH);
    }

}
