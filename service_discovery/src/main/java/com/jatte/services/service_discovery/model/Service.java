package com.jatte.services.service_discovery.model;

import java.io.Serializable;

public class Service implements Serializable {

    private static final long serialVersionUID = 1L;

    private String serviceName;
    private String url;
    private String healthCheckUrl;

    public Service() {}

    public Service(String serviceName, String url, String healthCheckUrl) {
        this.serviceName = serviceName;
        this.url = url;
        this.healthCheckUrl = healthCheckUrl;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getUrl() {
        return url;
    }

    public String getHealthCheckUrl() {
        return healthCheckUrl;
    }

    @Override
    public String toString() {
        return "Service{" +
                "serviceName='" + serviceName + '\'' +
                ", url='" + url + '\'' +
                ", healthCheckUrl='" + healthCheckUrl + '\'' +
                '}';
    }
}