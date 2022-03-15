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

package com.github.shoothzj.dev.shell;

import com.github.shoothzj.dev.module.shell.IfConfigResult;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class IfConfigResultParserTest {

    @Test
    public void testParseIfConfigResultSuccess() throws Exception {
        IfConfigResult ifConfigResult = IfConfigResultParser.parse(List.of(
                "eth0: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>  mtu 1500",
                "        inet 172.17.0.2  netmask 255.255.0.0  broadcast 172.17.255.255",
                "        ether 02:42:ac:11:00:02  txqueuelen 0  (Ethernet)",
                "        RX packets 88638  bytes 123277280 (117.5 MiB)",
                "        RX errors 0  dropped 0  overruns 0  frame 0",
                "        TX packets 24127  bytes 1321422 (1.2 MiB)",
                "        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0"
        ));
        Map<String, String> ipMap = ifConfigResult.getIpMap();
        String eth0Ip = ipMap.get("eth0");
        Assert.assertEquals(eth0Ip, "172.17.0.2");
    }

}
