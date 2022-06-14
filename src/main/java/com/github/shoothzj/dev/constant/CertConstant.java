/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.github.shoothzj.dev.constant;


import java.io.File;

public class CertConstant {

    public static final String KEYTOOL_JKS_PEM_CONVERSION = "keytool -list -rfc -keystore %s ";

    public static final String GENERATE_CERT_PATH = "%s" + File.separator + "%s";

    public static final String KEYTOOL_JKS_P12_CONVERSION = "keytool -importkeystore -srckeystore %s -destkeystore %s -srcstoretype JKS -deststoretype PKCS12 -srcstorepass %s -deststorepass %s  -noprompt";

    public static final String KEYTOOL_PKCS12_TO_JKS = "keytool -importkeystore -srckeystore %s.p12 -srcstoretype pkcs12 -destkeystore %s.jks -srcstorepass %s -deststorepass %s";

    public static final String STOREPASS = "-storepass %s";

    public static final String OPENSSL_OUT_PUBLIC_PEM = "openssl pkcs12 -in %s -nokeys -out %s";

    public static final String OPENSSL_OUT_PRIVATE_PEM = "openssl pkcs12 -in %s -nodes -nocerts -out private1.pem";

    public static final String OPENSSL_OUTPUT_PKCS12 = "openssl pkcs12 -export -in %s -inkey %s -out %s.p12 -passin pass:%s -passout pass:%s";

    public static final String BEGIN_CERTIFICATE = "BEGIN CERTIFICATE";

    public static final String END_CERTIFICATE = "END CERTIFICATE";

    public static final String EXCEPTION = "Exception";

    public static final String TRUST_JKS_FILE_NAME = "trust.jks";

    public static final String KEY_JKS_FILE_NAME = "key.jks";

    public static final String TRUST_P12_FILE_NAME = "trust.p12";

    public static final String KEY_P12_FILE_NAME = "key.p12";

    public static String jks2P12Command(String trustFile, String path, String password, String fileName) {
        String genTrustPath = String.format(CertConstant.GENERATE_CERT_PATH, path, fileName);
        return String.format(CertConstant.KEYTOOL_JKS_P12_CONVERSION, trustFile, genTrustPath, password, password);
    }

}
