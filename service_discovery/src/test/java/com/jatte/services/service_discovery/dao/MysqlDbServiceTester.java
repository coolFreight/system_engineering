package com.jatte.services.service_discovery.dao;

import com.jatte.services.service_discovery.model.RegisteredService;
import com.jatte.services.service_discovery.model.Service;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
public class MysqlDbServiceTester {
    private ServiceRegistryDao mysqlServiceRegistyDao;

    @BeforeEach
    public void setup() throws SQLException {
        mysqlServiceRegistyDao = new MySqlServiceRegistryDaoImpl();
    }

    @Test
    public void testRollback() throws SQLException {
        List<Service> services = List.of(new Service("order-tinyurl.service-1", "localhost", "healthCheck"),
                new Service("payment-tinyurl.service", "localhost", "healthCheck"),
                new Service("order-tinyurl.service-1", "localhost", "healthCheck"));
        assertFalse(mysqlServiceRegistyDao.registerService(services));
        RegisteredService registeredService = mysqlServiceRegistyDao.queryServiceIp("order-tinyurl.service-1");
        assertNull(registeredService);
    }

    @Test
    public void testPersistence() throws SQLException {
        List<Service> services = List.of(new Service("order-tinyurl.service-1", "localhost", "healthCheck"),
                new Service("payment-tinyurl.service", "localhost", "healthCheck"),
                new Service("order-tinyurl.service-2", "localhost", "healthCheck"));
        mysqlServiceRegistyDao.registerService(services);
        RegisteredService registeredService = mysqlServiceRegistyDao.queryServiceIp("order-tinyurl.service-2");
        assertEquals("order-tinyurl.service-2", registeredService.getServiceName());
        assertEquals("localhost", registeredService.getUrl());
        assertEquals(null, registeredService.getUptime());
    }

    @AfterEach
    public void cleanUpDB() throws SQLException {
        mysqlServiceRegistyDao.removeService("order-tinyurl.service-1");
        mysqlServiceRegistyDao.removeService("payment-tinyurl.service");
        mysqlServiceRegistyDao.removeService("order-tinyurl.service-2");
    }
}
