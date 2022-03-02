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

import com.github.shoothzj.dev.module.shell.KubectlNodeResult;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class KubectlNodeResultParserTest {

    @Test
    public void testParseKubectlNodeResultSuccess() throws Exception {
        List<KubectlNodeResult> kubectlNodeResultList = KubectlNodeResultParser.parseFull(List.of(
                "NAME       STATUS   ROLES                  AGE   VERSION   INTERNAL-IP    EXTERNAL-IP"
                        + "   OS-IMAGE             KERNEL-VERSION     CONTAINER-RUNTIME",
                "minikube   Ready    control-plane,master   50d   v1.22.3   192.168.49.2   <none>"
                        + "        Ubuntu 20.04.2 LTS   5.10.76-linuxkit   docker://20.10.8"
        ));
        Assert.assertEquals(kubectlNodeResultList.size(), 1);
        KubectlNodeResult modeResult = kubectlNodeResultList.get(0);
        Assert.assertEquals(modeResult.getName(), "minikube");
    }

}
