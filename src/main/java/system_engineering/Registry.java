/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system_engineering;

import system_engineering.model.RegisteredService;

import java.net.URL;
import java.util.Set;

/**
 *
 * @author jovaughnlockridge1
 */
public interface Registry {
    void register(String serviceName, String ip, URL healthcheck);
    void deregister(String serviceName);
    RegisteredService query(String serviceName);
}
