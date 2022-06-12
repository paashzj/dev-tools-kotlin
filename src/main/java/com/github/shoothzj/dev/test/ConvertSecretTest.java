package com.github.shoothzj.dev.test;

import com.github.shoothzj.dev.secret.ConvertSecret;

public class ConvertSecretTest {
    public static void generate_pem_and_key() {
        // create test file:
        // openssl req -newkey rsa:2048 -x509 -keyout key.pem -out cert.pem -days 365
    }

    public static void main(String[] args) {
        ConvertSecret convertSecret = new ConvertSecret();
//        convertSecret.pem2jks("cert.pem", "key.pem", "this_is_password");
        convertSecret.jks2pem("cert.jks", "this_is_password");
    }
}
