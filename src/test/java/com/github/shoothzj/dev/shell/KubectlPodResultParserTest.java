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

import com.github.shoothzj.dev.module.shell.KubectlPodResult;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class KubectlPodResultParserTest {

    @Test
    public void testParseKubectlPodResultSuccess() throws Exception {
        List<KubectlPodResult> kubectlPodResultList = KubectlPodResultParser.parseFull(List.of(
                "NAME                         READY   STATUS    RESTARTS        AGE   IP" +
                        "           NODE       NOMINATED NODE   READINESS GATES",
                "network-7cf6bdcb86-pc2w2     1/1     Running   2 (3m48s ago)   50d" +
                        "   172.17.0.4   minikube   <none>           <none>",
                "zookeeper-54947bdd6b-xpnks   1/1     Running   2 (3m48s ago)   50d" +
                        "   172.17.0.3   minikube   <none>           <none>"
        ));
        Assert.assertEquals(kubectlPodResultList.size(), 2);
        KubectlPodResult networkPod = kubectlPodResultList.get(0);
        Assert.assertEquals(networkPod.getPodName(), "network-7cf6bdcb86-pc2w2");
        Assert.assertTrue(networkPod.isReady());
        KubectlPodResult zooKeeperPod = kubectlPodResultList.get(1);
        Assert.assertEquals(zooKeeperPod.getPodName(), "zookeeper-54947bdd6b-xpnks");
        Assert.assertTrue(zooKeeperPod.isReady());
    }

}