package com.jatte.services.registry.dao;

import com.jatte.services.registry.implementation.ServiceRegistry;
import com.jatte.services.registry.model.RegisteredService;
import com.jatte.services.registry.model.Service;
import org.glassfish.grizzly.http.server.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Scanner;

import static com.jatte.services.registry.StartServiceRegistry.BASE_URI;
import static com.jatte.services.registry.StartServiceRegistry.startServer;

public class ServiceRegistryTester {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRegistryTester.class);


    public static void main(String[] args) throws IOException {
        if(args.length != 0){
            LOGGER.info("Running Service Discovery Registry Rest service....");
            final HttpServer server = startServer();
            System.out.println(String.format("Jersey app started with WADL available at "
                    + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
            System.in.read();
            server.shutdownNow();
        }else {
            Scanner sc = new Scanner(System.in);
            ServiceRegistry sr = new ServiceRegistry(new MySqlServiceRegistryDaoImpl());
            do {
                printMenu();
                int command = sc.nextInt();
                switch (command) {
                    case 1:
                        System.out.println("Enter service name");
                        String serviceName = sc.next();
                        System.out.println("Enter service ip");
                        String ip = sc.next();
                        Service service = new Service(serviceName, ip, "");
                        sr.register(service);
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
            System.out.println("Shutting down service registry process");
        }
    }

    private static void printMenu(){
        System.out.println("Enter a number for a service selection: ");
        System.out.println("1) register a service");
        System.out.println("2) deregister a service");
        System.out.println("3) query a ip");
    }
}
