/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system_engineering;

import java.net.URL;
import java.util.Scanner;
import java.util.Set;

import system_engineering.dao.MySqlServiceRegistryDao;
import system_engineering.dao.ServiceRegistryDao;
import system_engineering.model.RegisteredService;

/**
 *
 *
 */
public class ServiceRegistry implements Registry {
    
    private final ServiceRegistryDao dao;
    
    public ServiceRegistry(ServiceRegistryDao dao){
        this.dao = dao;
    }


    /**
     *
     * health check is used to 
     *
     * @param serviceName
     * @param ip
     * @param healthCheckUrl
     */
    @Override
    public void register(String serviceName, String ip, URL healthCheckUrl) {
        dao.registerService(serviceName, ip);
    }

    @Override
    public void deregister(String serviceName) {
        dao.deregisterService(serviceName);
    }

    @Override
    public RegisteredService query(String serviceName) {
        return dao.queryServiceIp(serviceName);
    }
    
    
    
   
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ServiceRegistry sr = new ServiceRegistry(new MySqlServiceRegistryDao());
        do{
            printMenu();
            int command = sc.nextInt();
            switch(command){
                case 1 :
                    System.out.println("Enter service name");
                    String service = sc.next();
                    System.out.println("Enter service ip");
                    String ip = sc.next();
                    sr.register(service, ip, null);
                break;
                case 2:
                    System.out.println("Enter service name");
                    String deregisteredService = sc.next();
                    sr.deregister(deregisteredService);
                break;
                case 3:
                    System.out.println("Enter service name");
                    String queriedService = sc.next();
                    RegisteredService ips = sr.query(queriedService);
                break;
            }
        } while (!sc.nextLine().equals("quit"));
        System.out.println("Shuttind down service registry process");
    }
    
    private static void printMenu(){
        System.out.println("Enter a number for a service selection: ");
        System.out.println("1) register a service");
        System.out.println("2) deregister a service");
        System.out.println("3) query a ip");
    }
    
}
