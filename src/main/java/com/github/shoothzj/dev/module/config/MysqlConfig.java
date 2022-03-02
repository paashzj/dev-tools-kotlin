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

package com.github.shoothzj.dev.module.config;

public class MysqlConfig extends BaseConfig {

    private String k8sName;

    private String namespace;

    private String statefulSetName;

    public MysqlConfig() {
    }

    public MysqlConfig(String name, String k8sName, String namespace, String statefulSetName) {
        super(name);
        this.k8sName = k8sName;
        this.namespace = namespace;
        this.statefulSetName = statefulSetName;
    }

    public String getK8sName() {
        return k8sName;
    }

    public void setK8sName(String k8sName) {
        this.k8sName = k8sName;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getStatefulSetName() {
        return statefulSetName;
    }

    public void setStatefulSetName(String statefulSetName) {
        this.statefulSetName = statefulSetName;
    }
}
