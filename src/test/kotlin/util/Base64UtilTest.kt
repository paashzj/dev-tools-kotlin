/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache license, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */

package util

import org.testng.Assert
import org.testng.annotations.Test

class Base64UtilTest {

    @Test
    fun testBase64Encode() {
        val res = Base64Util.encode("abcd")
        Assert.assertEquals(res, "YWJjZA==")
    }

    @Test
    fun testBase64Decode() {
        val res = Base64Util.decode("YWJjZA==")
        Assert.assertEquals(res, "abcd")
    }

    @Test
    fun testBase64EncodeBytes() {
        val res = Base64Util.encode("abcd".toByteArray())
        Assert.assertEquals(res, byteArrayOf(89, 87, 74, 106, 90, 65, 61, 61))
    }

    @Test
    fun testBase64DecodeBytes() {
        val res = Base64Util.decode("YWJjZA==".toByteArray())
        Assert.assertEquals(res, byteArrayOf(97, 98, 99, 100))
    }
}
