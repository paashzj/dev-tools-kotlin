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

package com.github.shoothzj.dev.secret;

import com.github.shoothzj.dev.constant.CertConstant;
import com.github.shoothzj.javatool.util.IoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Base64;

public class ConvertSecret {
    private static final Logger log = LoggerFactory.getLogger(ConvertSecret.class);

    public String jks2pem(String trustStore, String keyStore, String password, String path) {
        try {
            // generate trust jks file
            String trustJksPath = String.format(CertConstant.GENERATE_CERT_PATH, path, CertConstant.TRUST_JKS_FILE_NAME);
            File trustFile = new File(trustJksPath);
            byte[] trustBytes = Base64.getDecoder().decode(trustStore);
            IoUtil.write(trustBytes, new FileOutputStream(trustFile));

            // generate trust pem file
            jks2pem(trustJksPath, password);

            // generate trust p12 file
            exeCmd(CertConstant.jks2P12Command(trustJksPath, path, password, CertConstant.TRUST_P12_FILE_NAME));

            // generate key jks file
            String keyJksPath = String.format(CertConstant.GENERATE_CERT_PATH, path, CertConstant.KEY_JKS_FILE_NAME);
            File keyFile = new File(keyJksPath);
            byte[] keyBytes = Base64.getDecoder().decode(keyStore);
            IoUtil.write(keyBytes, new FileOutputStream(keyFile));

            // generate key pem file
            jks2pem(keyJksPath, password);

            // generate key p12 file
            exeCmd(CertConstant.jks2P12Command(keyJksPath, path, password, CertConstant.KEY_P12_FILE_NAME));

            return "success.";
        } catch (Exception e) {
            log.error("jks failed to convert the pem.", e);
            return "fail.";
        }
    }

    public void jks2pem(String path, String password) {
        try {
            StringBuilder cmd = new StringBuilder();
            cmd.append(String.format(CertConstant.KEYTOOL_JKS_PEM_CONVERSION, path));
            if (!"".equals(password)) {
                cmd.append(String.format(CertConstant.STOREPASS, password));
            }
            parseJksAndGeneratePemFile(exeCmdGetInputStream(cmd.toString()), path);
        } catch (Exception e) {
            log.error("jks failed to convert the pem. ", e);
        }
    }

    /**
     * convert pem file to jks
     *
     * @param crtFile  cert file path
     * @param keyFile  key file path
     * @param password key file password
     * @return convert string result
     */
    public String pem2jks(String crtFile, String keyFile, String password) {
        // step 1: pem to PKCS12.
        // get file basename, target output filename
        String pkcsFilename;
        String[] pkcsFilenames = crtFile.split("\\.");
        if (pkcsFilenames.length == 0) {
            pkcsFilename = crtFile;
        } else {
            pkcsFilename = pkcsFilenames[0];
        }

        try {
            // build a command string such as: openssl pkcs12 -export -in cert.pem -inkey key.pem -name "certificate" -passin pass:this_is_password -passout pass:this_is_password
            exeCmd(String.format(CertConstant.OPENSSL_OUTPUT_PKCS12, crtFile, keyFile, pkcsFilename, password, password));
        } catch (Exception e) {
            log.error("pem failed to convert to pkcs12. ", e);
            return "pem file convert to pkcs12 failed.";
        }
        // step 2: PKCS12 to jks.
        try {
            // build a command string such as: keytool -importkeystore -srckeystore cert.p12 -srcstoretype pkcs12 -destkeystore cert.jks -srcstorepass this_is_password -deststorepass this_is_password
            exeCmd(String.format(CertConstant.KEYTOOL_PKCS12_TO_JKS, pkcsFilename, pkcsFilename, password, password));
        } catch (Exception e) {
            log.error("pkcs12 failed to convert to jks.", e);
            return "pkcs12 file convert to jks failed.";
        }
        return "success";
    }

    private void parseJksAndGeneratePemFile(InputStream is, String path) {
        String pemPath = path.replaceAll(".jks", ".pem");
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "GBK"));
             BufferedWriter out = new BufferedWriter(new FileWriter(pemPath))) {
            boolean flag = false;
            String content = br.readLine();
            if (content.contains(CertConstant.EXCEPTION)) {
                throw new Exception(content);
            }
            while (content != null) {
                if (!flag) {
                    flag = content.contains(CertConstant.BEGIN_CERTIFICATE);
                }
                if (flag) {
                    builder.append(content).append(System.lineSeparator());
                    out.write(content);
                    out.newLine();
                    flag = !content.contains(CertConstant.END_CERTIFICATE);
                }
                content = br.readLine();
            }
        } catch (Exception e) {
            builder.append("jks failed to convert the pem. ");
            if (e.getMessage().contains("keystore password was incorrect")) {
                builder.append("keystore password was incorrect");
            }
            if (e.getMessage().contains("密钥库文件不存在")) {
                builder.append(String.format("keystore file does not exist: %s", path));
            }
            if (e.getMessage().contains("keystore file does not exist")) {
                builder.append(String.format("keystore file does not exist: %s", path));
            }
            log.error("jks failed to convert the pem. {}", e.getMessage());
        }
        log.debug("the pem content : {}", builder);
    }

    private InputStream exeCmdGetInputStream(String cmd) throws Exception {
        Process process = Runtime.getRuntime().exec(cmd);
        return process.getInputStream();
    }

    private void exeCmd(String cmd) throws Exception {
        InputStream is = exeCmdGetInputStream(cmd);
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "GBK"));
        String content = br.readLine();
        log.info("exec cmd {} content is {}", cmd, content);
        if (content == null) {
            log.error("generate fail. cmd {}", cmd);
        } else {
            log.info("generate success. cmd {}", cmd);
        }
    }
}
