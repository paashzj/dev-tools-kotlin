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

package com.github.shoothzj.dev.storage;

import com.github.shoothzj.dev.module.config.KubernetesConfig;
import com.github.shoothzj.dev.module.config.SshStep;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class StorageK8sTest {

    @Test
    public void testDeserializeConfig() {
        Map<String, KubernetesConfig> map = StorageK8s.getInstance().deserialize("""
                {
                    "default":{
                        "name":"default",
                        "host":"localhost",
                        "port":22,
                        "sshStep":{
                            "username":"root",
                            "password":"",
                            "suUsername":"root",
                            "suPassword":""
                        }
                    }
                }
                """);
        Assert.assertEquals(map.size(), 1);
        KubernetesConfig config = map.get("default");
        Assert.assertEquals(config.getName(), "default");
        Assert.assertEquals(config.getHost(), "localhost");
        Assert.assertEquals(config.getPort(), 22);
        SshStep sshStep = config.getSshStep();
        Assert.assertEquals(sshStep.getUsername(), "root");
        Assert.assertEquals(sshStep.getPassword(), "");
        Assert.assertEquals(sshStep.getSuUsername(), "root");
        Assert.assertEquals(sshStep.getSuPassword(), "");
    }

}
