/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system_engineering.dao;

import system_engineering.model.RegisteredService;
import system_engineering.model.ServiceRegistryInput;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author jovaughnlockridge1
 */
public class StubServiceRegistryDao implements ServiceRegistryDao {
    private Map<String, Set<String>> serviceRegistry = new HashMap<>();

    @Override
    public boolean registerService(String serviceName, String ip) {
        Set<String> ips = serviceRegistry.getOrDefault(serviceName, new HashSet<>());
        serviceRegistry.putIfAbsent(serviceName, ips);
        return ips.add(ip);
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
    public boolean registerService(List<ServiceRegistryInput> registryInputs) {
        return false;
    }

    @Override
    public boolean deRregisterService(List<ServiceRegistryInput> registryInputs) {
        return false;
    }
}
