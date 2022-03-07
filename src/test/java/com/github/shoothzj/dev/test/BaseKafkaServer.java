package com.github.shoothzj.dev.test;

import com.github.shoothzj.test.kafka.TestKfkServer;

public class BaseKafkaServer {

    protected final TestKfkServer testKfkServer = new TestKfkServer();

    public void startServer() throws Exception {
        testKfkServer.start();
    }

    public void stopServer() throws Exception {
        testKfkServer.close();
    }

}
