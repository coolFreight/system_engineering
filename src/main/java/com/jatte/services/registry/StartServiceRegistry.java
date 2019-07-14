package com.jatte.services.registry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.jatte.services.registry.dao.MySqlServiceRegistryDaoImpl;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class StartServiceRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartServiceRegistry.class);
    public static final String BASE_URI = "http://192.168.1.152:8001/system_engineering/";


    public static HttpServer startServer() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
        provider.setMapper(mapper);
        final ResourceConfig rc = new ResourceConfig()
                .packages("com.jatte.services.registry.implementation")
                .register(provider);
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException, SQLException {
        LOGGER.info("Running Service Discovery Registry Rest service....");
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(5);
        HealthChecker healthChecker = new HealthChecker(new MySqlServiceRegistryDaoImpl());
        healthChecker.startHealthChecker();
        System.in.read();
        server.shutdownNow();
    }
}
