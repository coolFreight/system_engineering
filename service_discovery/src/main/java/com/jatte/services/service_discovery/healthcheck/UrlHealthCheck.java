package com.jatte.services.service_discovery.healthcheck;

import java.util.concurrent.*;

public class UrlHealthCheck implements HealthCheckTask {

    private ExecutorService taskSubmitter;
    private JHttpClient httpClient;
    private String url;
    private long timeout;

    public UrlHealthCheck(ExecutorService taskSubmitter, JHttpClient httpClient, String url, long timeout) {
        this.taskSubmitter = taskSubmitter;
        this.httpClient = httpClient;
        this.url = url;
        this.timeout = timeout;
    }

    public void run() {
        System.out.println("Calling the url endpoint");
        Future<HealthCheckResult> result =  taskSubmitter.submit(() -> httpClient.simulateHttpCall(), new HealthCheckResult(HealthCheckResult.HealthCheckStatus.HEALTHY, "good"));
        try {
            System.out.println("Getting result waiting "+timeout+" seconds");

            result.get(timeout, TimeUnit.SECONDS);
             System.out.println("Healthy system ");
        }catch (TimeoutException t) {
            t.printStackTrace();
            System.out.println("url is unhealthy");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
