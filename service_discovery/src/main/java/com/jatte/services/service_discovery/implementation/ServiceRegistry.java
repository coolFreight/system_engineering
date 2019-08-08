package com.jatte.services.service_discovery.implementation;

import java.sql.SQLException;
import java.time.LocalDateTime;

import com.jatte.services.service_discovery.dao.MySqlServiceRegistryDaoImpl;
import com.jatte.services.service_discovery.dao.ServiceRegistryDao;
import com.jatte.services.service_discovery.model.RegisteredService;
import com.jatte.services.service_discovery.model.Service;

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
        this.dao = new MySqlServiceRegistryDaoImpl();
    }

    /**
     *
     * health check URL_COLUMN is used to determine if a tinyurl.service is still responsive, if not the tinyurl.service will be deregistered
     *
     */
    @POST
    @Path("service_registry")
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public void register(Service service) {
        dao.registerService(service);
        LOGGER.info("Registered tinyurl.service {}", service);
    }

    @Override
    public void deregister(String serviceName) {
        try {
            LOGGER.info("Deregistering tinyurl.service {}", serviceName);
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

    @GET
    @Path("service_registry/ping")
    @Produces(MediaType.TEXT_PLAIN)
    @Override
    public String ping() {
        LOGGER.info("ping endpoint was hit at {} ", LocalDateTime.now());
        return "PONG";
    }

}
