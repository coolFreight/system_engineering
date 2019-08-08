package com.jatte.services.service_discovery.dao;

import com.jatte.services.service_discovery.implementation.ServiceRegistry;
import com.jatte.services.service_discovery.model.RegisteredService;
import com.jatte.services.service_discovery.model.Service;
import org.glassfish.grizzly.http.server.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Scanner;

import static com.jatte.services.service_discovery.StartServiceRegistry.BASE_URI;
import static com.jatte.services.service_discovery.StartServiceRegistry.startServer;

public class ServiceRegistryTester {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRegistryTester.class);


    public static void main(String[] args) throws IOException {
        if(args.length != 0){
            LOGGER.info("Running Service Discovery Registry Rest tinyurl.service....");
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
                        System.out.println("Enter tinyurl.service name");
                        String serviceName = sc.next();
                        System.out.println("Enter tinyurl.service ip");
                        String ip = sc.next();
                        Service service = new Service(serviceName, ip, "");
                        sr.register(service);
                        break;
                    case 2:
                        System.out.println("Enter tinyurl.service name");
                        String deregisteredService = sc.next();
                        sr.deregister(deregisteredService);
                        break;
                    case 3:
                        System.out.println("Enter tinyurl.service name");
                        String queriedService = sc.next();
                        RegisteredService ips = sr.query(queriedService);
                        break;
                }
            } while (!sc.nextLine().equals("quit"));
            System.out.println("Shutting down tinyurl.service registry process");
        }
    }

    private static void printMenu(){
        System.out.println("Enter a number for a tinyurl.service selection: ");
        System.out.println("1) register a tinyurl.service");
        System.out.println("2) deregister a tinyurl.service");
        System.out.println("3) query a ip");
    }
}
