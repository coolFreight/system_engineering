package com.jatte.services.registry.model;

import java.io.Serializable;

public class Service implements Serializable {

    private static final long serialVersionUID = 1L;

    private String serviceName;
    private String ip;

    public Service() {}

    public Service(String serviceName, String ip) {
        this.serviceName = serviceName;
        this.ip = ip;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getIp() {
        return ip;
    }
}