/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system_engineering.dao;

import system_engineering.model.RegisteredService;
import system_engineering.model.ServiceRegistryInput;

import java.util.List;
import java.util.Set;

/**
 *
 * @author jovaughnlockridge1
 */
public interface ServiceRegistryDao {
    boolean registerService(String serviceName, String ip);
    boolean deregisterService(String serviceName);
    RegisteredService queryServiceIp(String serviceName);
    boolean registerService(List<ServiceRegistryInput> registryInputs);
    boolean deRregisterService(List<ServiceRegistryInput> registryInputs);
}
