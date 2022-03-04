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

import com.github.shoothzj.dev.module.config.PulsarConfig;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class StoragePulsarTest {

    @Test
    public void testDeserializeConfig() {
        Map<String, PulsarConfig> map = StoragePulsar.getInstance().deserialize("""
                {
                    "default":{
                        "name":"default",
                        "k8sName":"default",
                        "namespace":"default",
                        "deployName":"pulsar",
                        "brokerHttpHost":"localhost",
                        "brokerHttpPort":8080,
                        "functionHttpHost":"localhost",
                        "functionHttpPort":8080
                    }
                }
                """);
        Assert.assertEquals(map.size(), 1);
        PulsarConfig config = map.get("default");
        Assert.assertEquals(config.getName(), "default");
        Assert.assertEquals(config.getK8sName(), "default");
        Assert.assertEquals(config.getNamespace(), "default");
        Assert.assertEquals(config.getDeployName(), "pulsar");
        Assert.assertEquals(config.getBrokerHttpHost(), "localhost");
        Assert.assertEquals(config.getBrokerHttpPort(), 8080);
        Assert.assertEquals(config.getFunctionHttpHost(), "localhost");
        Assert.assertEquals(config.getFunctionHttpPort(), 8080);
    }

}
