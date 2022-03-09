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

public class NetstatResult {

    private String proto;

    private String recvQ;

    private String sendQ;

    private String localAddress;

    private String state;

    public NetstatResult() {
    }

    public String getProto() {
        return proto;
    }

    public void setProto(String proto) {
        this.proto = proto;
    }

    public String getRecvQ() {
        return recvQ;
    }

    public void setRecvQ(String recvQ) {
        this.recvQ = recvQ;
    }

    public String getSendQ() {
        return sendQ;
    }

    public void setSendQ(String sendQ) {
        this.sendQ = sendQ;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "NetstatResult{"
                + "proto='" + proto + '\''
                + ", recvQ='" + recvQ + '\''
                + ", sendQ='" + sendQ + '\''
                + ", localAddress='" + localAddress + '\''
                + ", state='" + state + '\''
                + '}';
    }
}
