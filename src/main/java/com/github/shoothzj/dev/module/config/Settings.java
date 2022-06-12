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

import java.util.Objects;

public class Settings {
    public static final int DEFAULT_SSH_JUMP_TIMEOUT_SECONDS = 5;
    public static final int DEFAULT_SSH_EXECUTE_TIMEOUT_SECONDS = 20;
    public static final int DEFAULT_SSH_LOGIN_TIMEOUT_SECONDS = 5;
    private Integer sshExecuteTimeoutSeconds;
    private Integer sshJumpTimeoutSeconds;
    private Integer sshLoginTimeoutSeconds;
    private String jdk8X86FilePath;
    private String jdk11X86FilePath;
    private String jdk17X86FilePath;
    private String jdk8armFilePath;
    private String jdk11armFilePath;
    private String jdk17armFilePath;
    private String tcpDumpArmFilePath;
    private String tcpDumpX86FilePath;
    private String dumpFileDir;

    public Settings() {
    }

    public Settings(String sshExecuteTimeoutSeconds, String sshJumpTimeoutSeconds, String sshLoginTimeoutSeconds, String jdk8X86FilePath, String jdk11X86FilePath, String jdk17X86FilePath, String jdk8armilePath, String jdk11armFilePath, String jdk17armFilePath, String dumpFileDir, String tcpDumpArmFilePath, String tcpDumpX86FilePath) {
        this.sshExecuteTimeoutSeconds = Integer.parseInt(sshExecuteTimeoutSeconds);
        this.sshJumpTimeoutSeconds = Integer.parseInt(sshJumpTimeoutSeconds);
        this.sshLoginTimeoutSeconds = Integer.parseInt(sshLoginTimeoutSeconds);
        this.jdk8X86FilePath = jdk8X86FilePath;
        this.jdk11X86FilePath = jdk11X86FilePath;
        this.jdk17X86FilePath = jdk17X86FilePath;
        this.jdk8armFilePath = jdk8armilePath;
        this.jdk11armFilePath = jdk11armFilePath;
        this.jdk17armFilePath = jdk17armFilePath;
        this.tcpDumpArmFilePath = tcpDumpArmFilePath;
        this.tcpDumpX86FilePath = tcpDumpX86FilePath;
        this.dumpFileDir = dumpFileDir;
    }

    public int getSshLoginTimeoutSeconds() {
        return Objects.requireNonNullElse(sshLoginTimeoutSeconds, DEFAULT_SSH_LOGIN_TIMEOUT_SECONDS);
    }

    public void setSshLoginTimeoutSeconds(int sshLoginTimeoutSeconds) {
        this.sshLoginTimeoutSeconds = sshLoginTimeoutSeconds;
    }

    public void setSshExecuteTimeoutSeconds(int sshExecuteTimeoutSeconds) {
        this.sshExecuteTimeoutSeconds = sshExecuteTimeoutSeconds;
    }

    public int getSshJumpTimeoutSeconds() {
        return Objects.requireNonNullElse(sshJumpTimeoutSeconds, DEFAULT_SSH_JUMP_TIMEOUT_SECONDS);
    }

    public int getSshExecuteTimeoutSeconds() {
        return Objects.requireNonNullElse(sshExecuteTimeoutSeconds, DEFAULT_SSH_EXECUTE_TIMEOUT_SECONDS);
    }

    public void setSshJumpTimeoutSeconds(int sshJumpTimeoutSeconds) {
        this.sshJumpTimeoutSeconds = sshJumpTimeoutSeconds;
    }

    public String getJdk8X86FilePath() {
        return Objects.requireNonNullElse(jdk8X86FilePath, "");
    }

    public void setJdk8X86FilePath(String jdk8X86FilePath) {
        this.jdk8X86FilePath = jdk8X86FilePath;
    }

    public String getJdk11X86FilePath() {
        return Objects.requireNonNullElse(jdk11X86FilePath, "");
    }

    public void setJdk11X86FilePath(String jdk11X86FilePath) {
        this.jdk11X86FilePath = jdk11X86FilePath;
    }

    public String getJdk17X86FilePath() {
        return Objects.requireNonNullElse(jdk17X86FilePath, "");
    }

    public void setJdk17X86FilePath(String jdk17X86FilePath) {
        this.jdk17X86FilePath = jdk17X86FilePath;
    }

    public String getJdk8armFilePath() {
        return Objects.requireNonNullElse(jdk8armFilePath, "");
    }

    public void setJdk8armFilePath(String jdk8armFilePath) {
        this.jdk8armFilePath = jdk8armFilePath;
    }

    public String getJdk11armFilePath() {
        return Objects.requireNonNullElse(jdk11armFilePath, "");
    }

    public void setJdk11armFilePath(String jdk11armFilePath) {
        this.jdk11armFilePath = jdk11armFilePath;
    }

    public String getJdk17armFilePath() {
        return Objects.requireNonNullElse(jdk17armFilePath, "");
    }

    public void setJdk17armFilePath(String jdk17armFilePath) {
        this.jdk17armFilePath = jdk17armFilePath;
    }

    public String getDumpFileDir() {
        return Objects.requireNonNullElse(dumpFileDir, "");
    }

    public void setDumpFileDir(String dumpFileDir) {
        this.dumpFileDir = dumpFileDir;
    }

    public String getTcpDumpArmFilePath() {
        return Objects.requireNonNullElse(tcpDumpArmFilePath, "");
    }

    public void setTcpDumpArmFilePath(String tcpDumpArmFilePath) {
        this.tcpDumpArmFilePath = tcpDumpArmFilePath;
    }

    public String getTcpDumpX86FilePath() {
        return Objects.requireNonNullElse(tcpDumpX86FilePath, "");
    }

    public void setTcpDumpX86FilePath(String tcpDumpX86FilePath) {
        this.tcpDumpX86FilePath = tcpDumpX86FilePath;
    }

    @Override
    public String toString() {
        return "Settings{"
                + ", sshLoginTimeoutSeconds=" + sshLoginTimeoutSeconds
                + ", sshJumpTimeoutSeconds=" + sshJumpTimeoutSeconds
                + ", sshExecuteTimeoutSeconds=" + sshExecuteTimeoutSeconds
                + '}';
    }
}
