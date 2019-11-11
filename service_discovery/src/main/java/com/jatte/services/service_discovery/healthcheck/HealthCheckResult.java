package com.jatte.services.service_discovery.healthcheck;

public class HealthCheckResult {
    enum HealthCheckStatus {HEALTHY, UNHEALTHY}

    private HealthCheckStatus status;
    private String description;

    public HealthCheckResult(HealthCheckStatus status, String description) {
        this.status = status;
        this.description = description;
    }

    public HealthCheckStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}
