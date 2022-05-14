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

public class Settings {

    private int sshExecuteTimeoutSeconds;
    private int sshJumpTimeoutSeconds;
    private int sshLoginTimeoutSeconds;

    public Settings() {
    }

    public Settings(String sshExecuteTimeoutSeconds, String sshJumpTimeoutSeconds, String sshLoginTimeoutSeconds) {
        this.sshExecuteTimeoutSeconds = Integer.parseInt(sshExecuteTimeoutSeconds);
        this.sshJumpTimeoutSeconds = Integer.parseInt(sshJumpTimeoutSeconds);
        this.sshLoginTimeoutSeconds = Integer.parseInt(sshLoginTimeoutSeconds);
    }

    public int getSshExecuteTimeoutSeconds() {
        return sshExecuteTimeoutSeconds;
    }

    public void setSshExecuteTimeoutSeconds(int sshExecuteTimeoutSeconds) {
        this.sshExecuteTimeoutSeconds = sshExecuteTimeoutSeconds;
    }

    public int getSshJumpTimeoutSeconds() {
        return sshJumpTimeoutSeconds;
    }

    public void setSshJumpTimeoutSeconds(int sshJumpTimeoutSeconds) {
        this.sshJumpTimeoutSeconds = sshJumpTimeoutSeconds;
    }

    public int getSshLoginTimeoutSeconds() {
        return sshLoginTimeoutSeconds;
    }

    public void setSshLoginTimeoutSeconds(int sshLoginTimeoutSeconds) {
        this.sshLoginTimeoutSeconds = sshLoginTimeoutSeconds;
    }

    @Override
    public String toString() {
        return "Settings{"
                 + ", sshExecuteTimeoutSeconds=" + sshExecuteTimeoutSeconds
                 + ", sshJumpTimeoutSeconds=" + sshJumpTimeoutSeconds
                 + ", sshLoginTimeoutSeconds=" + sshLoginTimeoutSeconds
                 + '}';
    }
}
