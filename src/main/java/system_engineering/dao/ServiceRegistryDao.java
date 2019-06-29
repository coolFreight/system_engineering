/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system_engineering.dao;

import java.util.Set;

/**
 *
 * @author jovaughnlockridge1
 */
public interface ServiceRegistryDao {
    boolean registerService(String serviceName, String ip);
    boolean deregisterService(String serviceName);
    Set<String> queryServiceIp(String serviceName);
}
