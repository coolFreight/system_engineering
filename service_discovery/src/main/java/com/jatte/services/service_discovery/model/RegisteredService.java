package com.jatte.services.service_discovery.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class RegisteredService implements Serializable {
    private String serviceName;
    private String url;
    private Timestamp uptime;
    private String healthCheckUrl;


    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Timestamp getUptime() {
        return uptime;
    }

    public void setUptime(Timestamp uptime) {
        this.uptime = uptime;
    }

    public void setHealthCheckUrl(String healthCheckUrl) {
        this.healthCheckUrl = healthCheckUrl;
    }

    public String getHealthCheckUrl() {
        return healthCheckUrl;
    }

    @Override
    public String toString() {
        return "RegisteredService{" +
                "serviceName='" + serviceName + '\'' +
                ", url='" + url + '\'' +
                ", uptime=" + uptime +
                '}';
    }
}
