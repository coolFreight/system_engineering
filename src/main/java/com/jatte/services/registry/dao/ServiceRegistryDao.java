package com.jatte.services.registry.dao;



import com.jatte.services.registry.model.RegisteredService;
import com.jatte.services.registry.model.Service;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jovaughnlockridge1
 */
public interface ServiceRegistryDao {
    boolean registerService(Service service);
    boolean deregisterService(String serviceName) throws SQLException;
    RegisteredService queryServiceIp(String serviceName);
    boolean registerService(List<Service> registryInputs) throws SQLException;
    boolean deRregisterService(List<String> registryInputs);
}
