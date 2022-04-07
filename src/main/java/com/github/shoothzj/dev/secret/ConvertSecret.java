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
import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.util.Base64;

public class ConvertSecret {
    private static final Logger log = LoggerFactory.getLogger(ConvertSecret.class);


    public SecretResponseBody jks2pem(String path, String content, String password) {
        try {
            StringBuilder cmd = new StringBuilder();
            if ("".equals(path) && !"".equals(content)) {
                path = tempFile(content);
            }
            System.out.println("the result : " + path);
            cmd.append(String.format(Constant.KEYTOOL_JKS_PEM_CONVERSION, path));
            if (!"".equals(password)) {
                cmd.append(String.format(Constant.STOREPASS, password));
            }
            InputStream is = exeCmd(cmd.toString());
            SecretResponseBody secretResponseBody = parseJksAndGeneratePemFile(is, path);
            System.out.println("content: " + secretResponseBody.getContent());
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
        return file.getAbsolutePath();
    }

    private SecretResponseBody parseJksAndGeneratePemFile(InputStream is, String path) {
        StringBuilder builder = new StringBuilder();
        InputStreamReader isr = null;
        BufferedWriter out = null;
        try {
            isr = new InputStreamReader(is, "GBK");
            BufferedReader br = new BufferedReader(isr);
            boolean flag = false;
            String content = br.readLine();
            if (content.contains(Constant.EXCEPTION)) {
                throw new Exception(content);
            }
            String pemPath = path.replaceAll(".jks", ".pem");
            out = new BufferedWriter(new FileWriter(pemPath));
            while (content != null) {
                if (!flag) {
                    flag = content.contains(Constant.BEGIN_CERTIFICATE);
                }
                if (flag) {
                    builder.append(content).append("\r\n");
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
        } finally {
            try {
                if (isr != null) {
                    isr.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                log.error("fail to close buffered.", e);
            }
        }
    }

    private InputStream exeCmd(String cmd) throws Exception {
        Process process = Runtime.getRuntime().exec(cmd);
        return process.getInputStream();
    }
}
