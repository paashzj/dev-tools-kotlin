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

    public static final String K8S_CONF_PATH = STORAGE_PREFIX + "kubernetes-conf-v1.json";

    public static final String BOOKKEEPER_CONF_PATH = STORAGE_PREFIX + "bookkeeper-conf-v1.json";
    public static final String CASSANDRA_CONF_PATH = STORAGE_PREFIX + "cassandra-conf-v1.json";
    public static final String MINIO_CONF_PATH = STORAGE_PREFIX + "minio-conf-v1.json";
    public static final String MYSQL_CONF_PATH = STORAGE_PREFIX + "mysql-conf-v1.json";
    public static final String NGX_CONF_PATH = STORAGE_PREFIX + "nginx-conf-v1.json";
    public static final String PULSAR_CONF_PATH = STORAGE_PREFIX + "pulsar-conf-v1.json";
    public static final String REDIS_CONF_PATH = STORAGE_PREFIX + "redis-conf-v1.json";
    public static final String ZK_CONF_PATH = STORAGE_PREFIX + "zookeeper-conf-v1.json";

    public static final String LVS_CONF_PATH = STORAGE_PREFIX + "lvs-conf-v1.json";

    public static final String SETTING_PATH = STORAGE_PREFIX + "dev-tools-kotlin-conf-v1.json";

    public static final String SIMULATOR_PULSAR_CONFIG_PATH = STORAGE_PREFIX + "simulator-pulsar-conf-v1.json";

    public static final String SIMULATOR_KAFKA_CONFIG_PATH = STORAGE_PREFIX + "simulator-kafka-conf-v1.json";

    public static final String SIMULATOR_PULSAR_MSG_STORAGE_PATH = STORAGE_PREFIX + "simulator-pulsar-msg.txt";

    static {
        File storageDir = new File(STORAGE_DIR);
        storageDir.mkdirs();

        FileUtil.ensureFileExists(K8S_CONF_PATH);

        FileUtil.ensureFileExists(BOOKKEEPER_CONF_PATH);
        FileUtil.ensureFileExists(CASSANDRA_CONF_PATH);
        FileUtil.ensureFileExists(MINIO_CONF_PATH);
        FileUtil.ensureFileExists(MYSQL_CONF_PATH);
        FileUtil.ensureFileExists(NGX_CONF_PATH);
        FileUtil.ensureFileExists(PULSAR_CONF_PATH);
        FileUtil.ensureFileExists(REDIS_CONF_PATH);
        FileUtil.ensureFileExists(ZK_CONF_PATH);
        FileUtil.ensureFileExists(LVS_CONF_PATH);

        FileUtil.ensureFileExists(SIMULATOR_KAFKA_CONFIG_PATH);
        FileUtil.ensureFileExists(SIMULATOR_PULSAR_CONFIG_PATH);
        FileUtil.ensureFileExists(SIMULATOR_PULSAR_MSG_STORAGE_PATH);
    }

}
