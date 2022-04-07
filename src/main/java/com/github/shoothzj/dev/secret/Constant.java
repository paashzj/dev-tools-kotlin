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

package com.github.shoothzj.dev.secret;


public class Constant {

    public static final String KEYTOOL_JKS_PEM_CONVERSION = "keytool -list -rfc -keystore %s ";

    public static final String TEMP_PATH = "%s/%s";

    public static final String STOREPASS = "-storepass %s";

    public static final String BEGIN_CERTIFICATE = "BEGIN CERTIFICATE";

    public static final String END_CERTIFICATE = "END CERTIFICATE";

    public static final String PEM_FILE_PATH_DESCRIBE = "pem filepath : %s";

    public static final String EXCEPTION = "Exception";

}
