package com.github.shoothzj.dev.test;

import com.github.shoothzj.dev.secret.ConvertSecret;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ConvertSecretTest {
    @Test
    public static void jks2pem() {
        ConvertSecret convertSecret = new ConvertSecret();
        convertSecret.jks2pem("cert.jks", "this_is_password");
    }
}
