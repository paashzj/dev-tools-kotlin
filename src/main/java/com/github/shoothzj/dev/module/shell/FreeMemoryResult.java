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

public class FreeMemoryResult {

    private long memTotalBytes;

    private long memUsedBytes;

    private long memFreeBytes;

    private long memSharedBytes;

    private long memBufferCacheBytes;

    private long memAvailableBytes;

    public FreeMemoryResult() {
    }

    public long getMemTotalBytes() {
        return memTotalBytes;
    }

    public void setMemTotalBytes(long memTotalBytes) {
        this.memTotalBytes = memTotalBytes;
    }

    public long getMemUsedBytes() {
        return memUsedBytes;
    }

    public void setMemUsedBytes(long memUsedBytes) {
        this.memUsedBytes = memUsedBytes;
    }

    public long getMemFreeBytes() {
        return memFreeBytes;
    }

    public void setMemFreeBytes(long memFreeBytes) {
        this.memFreeBytes = memFreeBytes;
    }

    public long getMemSharedBytes() {
        return memSharedBytes;
    }

    public void setMemSharedBytes(long memSharedBytes) {
        this.memSharedBytes = memSharedBytes;
    }

    public long getMemBufferCacheBytes() {
        return memBufferCacheBytes;
    }

    public void setMemBufferCacheBytes(long memBufferCacheBytes) {
        this.memBufferCacheBytes = memBufferCacheBytes;
    }

    public long getMemAvailableBytes() {
        return memAvailableBytes;
    }

    public void setMemAvailableBytes(long memAvailableBytes) {
        this.memAvailableBytes = memAvailableBytes;
    }

    @Override
    public String toString() {
        return "FreeMemoryResult{" +
                "memTotalBytes=" + memTotalBytes +
                ", memUsedBytes=" + memUsedBytes +
                ", memFreeBytes=" + memFreeBytes +
                ", memSharedBytes=" + memSharedBytes +
                ", memBufferCacheBytes=" + memBufferCacheBytes +
                ", memAvailableBytes=" + memAvailableBytes +
                '}';
    }
}
