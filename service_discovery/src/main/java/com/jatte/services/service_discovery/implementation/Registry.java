/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jatte.services.service_discovery.implementation;


import com.jatte.services.service_discovery.model.RegisteredService;
import com.jatte.services.service_discovery.model.Service;



/**
 *
 * @author jovaughn lockridge
 */
public interface Registry {
    void register(Service service);
    void deregister(String serviceName);
    RegisteredService query(String serviceName);
    String ping();
}
