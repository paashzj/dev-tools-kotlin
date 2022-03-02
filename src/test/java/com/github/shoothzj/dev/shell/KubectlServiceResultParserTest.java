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

import com.github.shoothzj.dev.module.shell.KubectlServiceResult;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class KubectlServiceResultParserTest {

    @Test
    public void testParseKubectlServiceResultSuccess() throws Exception {
        List<KubectlServiceResult> kubectlServiceResultList = KubectlServiceResultParser.parseFull(List.of(
                "NAME         TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)                      AGE   SELECTOR",
                "zookeeper    ClusterIP   None         <none>        2181/TCP,2888/TCP,3888/TCP   50d   app=zookeeper"
        ));
        Assert.assertEquals(kubectlServiceResultList.size(), 1);
        KubectlServiceResult serviceResult = kubectlServiceResultList.get(0);
        Assert.assertEquals(serviceResult.getName(), "zookeeper");
    }

}
