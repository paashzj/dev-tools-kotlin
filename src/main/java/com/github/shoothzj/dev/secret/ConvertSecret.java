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

import com.github.shoothzj.javatool.util.IoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Base64;

public class ConvertSecret {
    private static final Logger log = LoggerFactory.getLogger(ConvertSecret.class);

    public String jks2pem(String trustStore, String keyStore, String password, String path) {
        try {
            // generate trust jks file
            String trustJksPath = String.format(Constant.GENERATE_CERT_PATH, path, Constant.TRUST_JKS_FILE_NAME);
            File trustFile = new File(trustJksPath);
            byte[] trustBytes = Base64.getDecoder().decode(trustStore);
            IoUtil.write(trustBytes, new FileOutputStream(trustFile));

            // generate trust pem file
            jks2pem(trustJksPath, password);

            // generate trust p12 file
            exeCmd1(Constant.generateP12(trustJksPath, path, password, Constant.TRUST_P12_FILE_NAME));


            //generate trust public key file
            //  exeCmd1(Constant.opensslOutPublicPemCmd(genTrustPath, path, Constant.TRUST_PUBLIC_PEM_NAME));

            // generate trust private key file
            // exeCmd1(Constant.opensslOutPrivatePemCmd(genTrustPath, path, Constant.TRUST_PRIVATE_PEM_NAME));

            // generate key jks file
            String keyJksPath = String.format(Constant.GENERATE_CERT_PATH, path, Constant.KEY_JKS_FILE_NAME);
            File keyFile = new File(keyJksPath);
            byte[] KeyBytes = Base64.getDecoder().decode(keyStore);
            IoUtil.write(KeyBytes, new FileOutputStream(keyFile));

            // generate key pem file
            jks2pem(keyJksPath, password);

            // generate key p12 file
            exeCmd1(Constant.generateP12(keyJksPath, path, password, Constant.KEY_P12_FILE_NAME));

            //generate trust public key file
            //exeCmd1(Constant.opensslOutPublicPemCmd(genKeyPath, path, Constant.KEY_PUBLIC_PEM_NAME));

            //generate key private key file
            //exeCmd1(Constant.opensslOutPrivatePemCmd(genKeyPath, path, Constant.KEY_PRIVATE_PEM_NAME));
            return "success.";
        } catch (Exception e) {
            log.error("jks failed to convert the pem.", e);
            return "fail.";
        }
    }

    public void jks2pem(String path, String password) {
        try {
            StringBuilder cmd = new StringBuilder();
            cmd.append(String.format(Constant.KEYTOOL_JKS_PEM_CONVERSION, path));
            if (!"".equals(password)) {
                cmd.append(String.format(Constant.STOREPASS, password));
            }
            parseJksAndGeneratePemFile(exeCmd(cmd.toString()), path);
        } catch (Exception e) {
            log.error("jks failed to convert the pem. ", e);
        }
    }

    private void parseJksAndGeneratePemFile(InputStream is, String path) {
        String pemPath = path.replaceAll(".jks", ".pem");
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "GBK"));
             BufferedWriter out = new BufferedWriter(new FileWriter(pemPath))) {
            boolean flag = false;
            String content = br.readLine();
            if (content.contains(Constant.EXCEPTION)) {
                throw new Exception(content);
            }
            while (content != null) {
                if (!flag) {
                    flag = content.contains(Constant.BEGIN_CERTIFICATE);
                }
                if (flag) {
                    builder.append(content).append(System.lineSeparator());
                    out.write(content);
                    out.newLine();
                    flag = !content.contains(Constant.END_CERTIFICATE);
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

    private InputStream exeCmd(String cmd) throws Exception {
        Process process = Runtime.getRuntime().exec(cmd);
        return process.getInputStream();
    }

    private void exeCmd1(String cmd) throws Exception {
        InputStream is = exeCmd(cmd);
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "GBK"));
        String content = br.readLine();
        System.out.println(cmd + " : " + content);
        if (content == null) {
            log.error("generate fail. cmd {}", cmd);
        } else {
            log.info("generate success. cmd {}", cmd);
        }
    }
}
