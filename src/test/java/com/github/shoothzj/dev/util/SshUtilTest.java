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

package com.github.shoothzj.dev.util;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class SshUtilTest {

    @Test
    public void deleteFirstLastLineShort() {
        List<String> list = SshUtil.deleteFirstLastLine(new ArrayList<>());
        Assert.assertEquals(list.size(), 0);
    }

    @Test
    public void deleteFirstLastLine() {
        List<String> list = SshUtil.deleteFirstLastLine(List.of(
                "[root@iZuf6e1qguryhi8gxdr2fqZ ~]# free -m",
                "              total        used        free      shared  buff/cache   available",
                "Mem:            759         160         215           1         383         464",
                "Swap:             0           0           0",
                "[root@iZuf6e1qguryhi8gxdr2fqZ ~]#"
        ));
        Assert.assertEquals(list.size(), 3);
    }

}