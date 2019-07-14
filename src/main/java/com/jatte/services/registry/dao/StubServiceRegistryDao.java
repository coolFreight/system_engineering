/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jatte.services.registry.dao;


import com.jatte.services.registry.model.RegisteredService;
import com.jatte.services.registry.model.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author jovaughn lockridge
 */
public class StubServiceRegistryDao implements ServiceRegistryDao {
    private Map<String, Set<String>> serviceRegistry = new HashMap<>();

    @Override
    public boolean registerService(Service service) {
        Set<String> ips = serviceRegistry.getOrDefault(service.getServiceName(), new HashSet<>());
        serviceRegistry.putIfAbsent(service.getServiceName(), ips);
        return ips.add(service.getUrl());
    }

    @Override
    public boolean deregisterService(String serviceName) {
        if (serviceRegistry.containsKey(serviceName)) {
            serviceRegistry.remove(serviceName);
            System.out.println(serviceName + " has been deregistered");
            return true;
        }
        System.out.println(serviceName + " is not registered.");
        return false;
    }

    @Override
    public RegisteredService queryServiceIp(String serviceName) {
        RegisteredService registeredService = new RegisteredService();
        return registeredService;
    }

    @Override
    public boolean registerService(List<Service> registryInputs) {
        return false;
    }

    @Override
    public boolean deRregisterService(List<String> registryInputs) {
        return false;
    }

    @Override
    public List<RegisteredService> getRegisteredServices() {
        return null;
    }

    @Override
    public boolean removeService(String service) throws SQLException {
        return false;
    }
}
