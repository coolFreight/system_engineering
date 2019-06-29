/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system_engineering.dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author jovaughnlockridge1
 */
public class StubServiceRegistryDao implements ServiceRegistryDao {
    private Map<String, Set<String>> serviceRegistry = new HashMap<>();
    
    @Override
    public boolean registerService(String serviceName, String ip) {
        Set<String> ips = serviceRegistry.getOrDefault(serviceName, new HashSet<String>());
        return ips.add(serviceName);
    }

    @Override
    public boolean deregisterService(String serviceName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> queryServiceIp(String serviceName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
