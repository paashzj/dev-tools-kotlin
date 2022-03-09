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

package com.github.shoothzj.dev.constant;

import org.testng.Assert;
import org.testng.annotations.Test;

public class K8sCmdConstTest {

    @Test
    public void testPodListDefault() {
        String cmd = K8sCmdConst.podListCmd("");
        Assert.assertEquals(cmd, "kubectl get pod -o wide -n default");
    }

    @Test
    public void testPodListSystem() {
        String cmd = K8sCmdConst.podListCmd(K8sConst.SYSTEM_NS);
        Assert.assertEquals(cmd, "kubectl get pod -o wide -n kube-system");
    }

    @Test
    public void testPodListDefaultGrepCoredns() {
        String cmd = K8sCmdConst.podListGrepCmd("", "coredns");
        Assert.assertEquals(cmd, "kubectl get pod -o wide -n default| grep coredns");
    }

    @Test
    public void testPodListSystemGrepCoredns() {
        String cmd = K8sCmdConst.podListGrepCmd(K8sConst.SYSTEM_NS, "coredns");
        Assert.assertEquals(cmd, "kubectl get pod -o wide -n kube-system| grep coredns");
    }

    @Test
    public void testDescribeDefaultCoredns() {
        String cmd = K8sCmdConst.describePodCmd("", "coredns");
        Assert.assertEquals(cmd, "kubectl describe pod coredns -n default");
    }

    @Test
    public void testDescribeSystemCoredns() {
        String cmd = K8sCmdConst.describePodCmd(K8sConst.SYSTEM_NS, "coredns");
        Assert.assertEquals(cmd, "kubectl describe pod coredns -n kube-system");
    }

    @Test
    public void testExecNetstat() {
        String cmd = K8sCmdConst.execPodCmd("", "coredns", "netstat -anp");
        Assert.assertEquals(cmd, "kubectl exec -it coredns -n default -- netstat -anp");
    }

}
