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

public class ValidateUtilTest {

    @Test
    public void invalidHostname() {
        Assert.assertTrue(ValidateUtil.isNotHost("localhost.abc"));
    }

    @Test
    public void invalidIp() {
        Assert.assertTrue(ValidateUtil.isNotHost("256.256.256.256"));
    }

    @Test
    public void invalidPortNotNumber() {
        Assert.assertTrue(ValidateUtil.isNotPort("abcd"));
    }

    @Test
    public void invalidPortMinus() {
        Assert.assertTrue(ValidateUtil.isNotPort("-1"));
    }

    @Test
    public void invalidPortBig() {
        Assert.assertTrue(ValidateUtil.isNotPort("65536"));
    }

}
