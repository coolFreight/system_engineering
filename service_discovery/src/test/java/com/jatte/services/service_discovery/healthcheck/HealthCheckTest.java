package com.jatte.services.service_discovery.healthcheck;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.concurrent.*;


public class HealthCheckTest {

    JHttpClient httpClient = new JHttpClient() {
        @Override
        public boolean simulateHttpCall() {
            System.out.println("Sleeping for 10 seconds");
            try {
                Thread.sleep(10000);
                return true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }
    };

    private HealthChecker healthChecker = new HealthChecker();


    @Test
    public void testUrlHealthCheck() {
        UrlHealthCheck healthCheckTask = new UrlHealthCheck(Executors.newFixedThreadPool(1), httpClient, "blah", 15);
        healthChecker.registerHealthCheck(healthCheckTask, 1, 45 );
        while (true) {
            Thread.onSpinWait();
        }
    }
}
