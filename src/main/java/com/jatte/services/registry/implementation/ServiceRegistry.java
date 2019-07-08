package com.jatte.services.registry.implementation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;


import com.jatte.services.registry.dao.MySqlServiceRegistryDao;
import com.jatte.services.registry.dao.ServiceRegistryDao;
import com.jatte.services.registry.model.RegisteredService;
import com.jatte.services.registry.model.Service;
import org.glassfish.grizzly.http.server.HttpServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static com.jatte.services.registry.StartServiceRegistry.BASE_URI;
import static com.jatte.services.registry.StartServiceRegistry.startServer;

/**
 *
 *
 */
@Path("/")
public class ServiceRegistry implements Registry {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRegistry.class);
    private final ServiceRegistryDao dao;

    public ServiceRegistry(ServiceRegistryDao dao) {
        this.dao = dao;
    }

    /**
     *
     * Tightly coupled for testing purposes.
     *
     */
    public ServiceRegistry(){
        this.dao = new MySqlServiceRegistryDao();
    }

    /**
     *
     * health check URL is used to determine if a service is still responsive, if not the service will be deregistered
     *
     */
    @POST
    @Path("service_registry")
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public void register(Service service) {
        dao.registerService(service);
    }

    @Override
    public void deregister(String serviceName) {
        try {
            dao.deregisterService(serviceName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @GET
    @Path("service_registry/query/{serviceName}")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public RegisteredService query(@PathParam("serviceName") String serviceName) {
        return dao.queryServiceIp(serviceName);
    }

    public static void main(String[] args) throws IOException {
        if(args.length != 0){
            LOGGER.info("Running Service Discovery Registry Rest service....");
            final HttpServer server = startServer();
            System.out.println(String.format("Jersey app started with WADL available at "
                    + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
            System.in.read();
            server.stop();
        }else {
            Scanner sc = new Scanner(System.in);
            ServiceRegistry sr = new ServiceRegistry(new MySqlServiceRegistryDao());
            do {
                printMenu();
                int command = sc.nextInt();
                switch (command) {
                    case 1:
                        System.out.println("Enter service name");
                        String serviceName = sc.next();
                        System.out.println("Enter service ip");
                        String ip = sc.next();
                        Service service = new Service(serviceName, ip);
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
