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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ConvertSecret {
    private static final Logger log = LoggerFactory.getLogger(ConvertSecret.class);

    public SecretResponseBody jks2pem(String trustStore, String keyStore, String password, String path) {
        try {
            generateP12(trustStore, path, password, Constant.TRUST_P12_FILE_NAME);
            generateP12(keyStore, path, password, Constant.KEY_P12_FILE_NAME);
        } catch (Exception e) {
            log.error("jks failed to convert the pem.");
        }

        return null;
    }

    private void generateP12(String store, String path, String password, String generatePath) throws Exception {
        String keyTempPath = tempFile(store);
        String genPath = String.format(Constant.GENERATE_CERT_PATH, path, generatePath);
        String exportCmd = String.format(Constant.KEYTOOL_JKS_P12_CONVERSION, keyTempPath, genPath, password, password);
        InputStream is = exeCmd(exportCmd);
    }

    public SecretResponseBody jks2pem(String path, String content, String password) {
        try {
            StringBuilder cmd = new StringBuilder();
            if ("".equals(path) && !"".equals(content)) {
                path = tempFile(content);
            }
            cmd.append(String.format(Constant.KEYTOOL_JKS_PEM_CONVERSION, path));
            if (!"".equals(password)) {
                cmd.append(String.format(Constant.STOREPASS, password));
            }
            InputStream is = exeCmd(cmd.toString());
            return parseJksAndGeneratePemFile(is, path);
        } catch (Exception e) {
            log.error("jks failed to convert the pem. ", e);
            return new SecretResponseBody("jks failed to convert the pem.", "");
        }
    }

    private String tempFile(String content) throws IOException {
        File file = File.createTempFile("temp-file", ".jks");
        file.deleteOnExit();
        byte[] bytes = Base64.getDecoder().decode(content);
        IoUtil.write(bytes, new FileOutputStream(file));
        log.info("the file absolute path : {}", file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    private SecretResponseBody parseJksAndGeneratePemFile(InputStream is, String path) {
        String pemPath = path.replaceAll(".jks", ".pem");
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
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
            return new SecretResponseBody(String.format(Constant.PEM_FILE_PATH_DESCRIBE, pemPath), builder.toString());
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
            return new SecretResponseBody("fail to generate pem file.", builder.toString());
        }
    }

    private InputStream exeCmd(String cmd) throws Exception {
        Process process = Runtime.getRuntime().exec(cmd);
        return process.getInputStream();
    }
}
