package com.jatte.services.registry;

import com.jatte.services.registry.dao.ServiceRegistryDao;
import com.jatte.services.registry.model.RegisteredService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

public class HealthChecker {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthChecker.class);
    private ServiceRegistryDao dbDao;
    private ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(5);
    ExecutorService healthCheckThreads = Executors.newFixedThreadPool(5);


    public HealthChecker(ServiceRegistryDao dao) {
        this.dbDao = dao;
    }

    public void startHealthChecker() throws SQLException {
        threadPool.scheduleAtFixedRate(() -> performHealthCheckProcess(), 30, 50, TimeUnit.SECONDS);

    }

    public void performHealthCheckProcess() {
        List<RegisteredService> registeredServiceList = dbDao.getRegisteredServices();
        for (RegisteredService service : registeredServiceList) {
            healthCheckThreads.submit(new HealthCheckCallable(service));
        }
    }

    private class HealthCheckCallable implements Callable<Boolean> {
        private RegisteredService registeredService;

        public HealthCheckCallable(RegisteredService registeredService) {
            this.registeredService = registeredService;
        }

        public Boolean call() throws SQLException {
            URL healthCheck = null;
            boolean successfulConnection = false;
            HttpURLConnection httpURLConnection = null;
            String registeredServiceUrl = registeredService.getHealthCheckUrl() != null ? registeredService.getHealthCheckUrl().trim() : "";
            String serviceName = registeredService.getServiceName();
            try {
                LOGGER.info("Pinging health url={} for service={}", registeredServiceUrl, serviceName);
                healthCheck = new URL(registeredServiceUrl);
                httpURLConnection = (HttpURLConnection) healthCheck.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                successfulConnection = true;
                LOGGER.error("Pinged service {} response code {} ", serviceName, httpURLConnection.getResponseCode());
            } catch (IOException e) {
                LOGGER.error("Could not establish a connection to health check URL_COLUMN {} for service {} ", registeredServiceUrl, serviceName, e);
            }
            try {
                Optional<HttpURLConnection> optionalReponseCode = Optional.ofNullable(httpURLConnection);
                if (!successfulConnection || httpURLConnection.getResponseCode() != 200) {
                    LOGGER.error("Evicting service {} response code {} ", serviceName, optionalReponseCode.isPresent() ? optionalReponseCode.get().getResponseCode() : -1);
                    dbDao.deregisterService(serviceName);
                }
            } catch (IOException e) {
                LOGGER.error("Could not establish a connection to health check URL_COLUMN {} for service {} ", registeredServiceUrl, serviceName, e);
            } finally {
                return successfulConnection;
            }
        }
    }
}
