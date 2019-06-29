/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system_engineering;

/**
 *
 * @author jovaughnlockridge1
 */
public class Service {
    private final String serviceName;
    private final String ip;

    public Service(String serviceName, String ipLocation) {
        this.serviceName = serviceName;
        this.ip = ipLocation;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getIp() {
        return ip;
    } 
}
