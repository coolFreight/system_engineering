package com.jatte.services.service_discovery.dao;


import com.jatte.services.service_discovery.model.RegisteredService;
import com.jatte.services.service_discovery.model.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * @author jovaughn lockridge
 */
public interface ServiceRegistryDao {
    boolean registerService(Service service);

    boolean deregisterService(String serviceName) throws SQLException;

    RegisteredService queryServiceIp(String serviceName);

    boolean registerService(List<Service> registryInputs) throws SQLException;

    boolean deRregisterService(List<String> registryInputs);

    List<RegisteredService> getRegisteredServices();

    boolean removeService(String service) throws SQLException;
}
