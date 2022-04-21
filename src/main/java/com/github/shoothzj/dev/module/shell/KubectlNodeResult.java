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

package com.github.shoothzj.dev.module.shell;

public class KubectlNodeResult {

    private String name;

    private KubectlNodeStatusEnum status;

    private String roles;

    private String age;

    private String version;

    private String internalIp;

    private String externalIp;

    private String osImage;

    private String kernelVersion;

    private String containerRuntime;

    public KubectlNodeResult() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KubectlNodeStatusEnum getStatus() {
        return status;
    }

    public void setStatus(KubectlNodeStatusEnum status) {
        this.status = status;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getInternalIp() {
        return internalIp;
    }

    public void setInternalIp(String internalIp) {
        this.internalIp = internalIp;
    }

    public String getExternalIp() {
        return externalIp;
    }

    public void setExternalIp(String externalIp) {
        this.externalIp = externalIp;
    }

    public String getOsImage() {
        return osImage;
    }

    public void setOsImage(String osImage) {
        this.osImage = osImage;
    }

    public String getKernelVersion() {
        return kernelVersion;
    }

    public void setKernelVersion(String kernelVersion) {
        this.kernelVersion = kernelVersion;
    }

    public String getContainerRuntime() {
        return containerRuntime;
    }

    public void setContainerRuntime(String containerRuntime) {
        this.containerRuntime = containerRuntime;
    }

    @Override
    public String toString() {
        return "KubectlNodeResult{"
                + "name='" + name + '\''
                + ", status=" + status
                + ", roles='" + roles + '\''
                + ", age='" + age + '\''
                + ", version='" + version + '\''
                + ", internalIp='" + internalIp + '\''
                + ", externalIp='" + externalIp + '\''
                + ", osImage='" + osImage + '\''
                + ", kernelVersion='" + kernelVersion + '\''
                + ", containerRuntime='" + containerRuntime + '\''
                + '}';
    }
}
