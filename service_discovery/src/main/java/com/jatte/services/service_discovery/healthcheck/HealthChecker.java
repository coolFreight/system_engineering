package com.jatte.services.service_discovery.healthcheck;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HealthChecker {

    private Executor taskSubmitter = Executors.newFixedThreadPool(1);
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();


    public void registerHealthCheck(HealthCheckTask task,  long initialDelay, int interval) {
        scheduler.scheduleAtFixedRate(task, initialDelay, interval, TimeUnit.SECONDS);
    }
}
