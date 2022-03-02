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

import com.github.shoothzj.dev.module.shell.KubectlDeployResult;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class KubectlDeployResultParserTest {

    @Test
    public void testParseKubectlDeployResultSuccess() throws Exception {
        List<KubectlDeployResult> kubectlDeployResultList = KubectlDeployResultParser.parseFull(List.of(
                "NAME        READY   UP-TO-DATE   AVAILABLE   AGE   CONTAINERS   IMAGES                     SELECTOR",
                "network     1/1     1            1           50d   network      ttbb/network-tool:latest   app=network"
        ));
        Assert.assertEquals(kubectlDeployResultList.size(), 1);
        KubectlDeployResult deployResult = kubectlDeployResultList.get(0);
        Assert.assertEquals(deployResult.getName(), "network");
    }

}
