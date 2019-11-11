package com.jatte.services.service_discovery.healthcheck;

public interface HealthCheckTask extends Runnable {

    @Override
    void run();

}
