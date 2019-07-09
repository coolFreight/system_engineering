package com.jatte.services.registry.implementation;

import java.sql.SQLException;

import com.jatte.services.registry.dao.MySqlServiceRegistryDao;
import com.jatte.services.registry.dao.ServiceRegistryDao;
import com.jatte.services.registry.model.RegisteredService;
import com.jatte.services.registry.model.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
            LOGGER.info("Deregistering service {}", serviceName);
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

}
