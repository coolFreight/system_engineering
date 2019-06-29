/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system_engineering;

import java.util.List;

/**
 *
 * @author jovaughnlockridge1
 */
public interface Registry {
    void register(String serviceName, String ip);
    void deregister(String serviceName);
    List<String> query(String serviceName);
}
