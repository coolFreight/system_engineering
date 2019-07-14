package com.jatte.services.service_discovery.dao;

import org.junit.*;
import com.jatte.services.service_discovery.model.RegisteredService;
import com.jatte.services.service_discovery.model.Service;

import java.sql.SQLException;
import java.util.List;

public class MysqlDbServiceTester {
    private ServiceRegistryDao mysqlServiceRegistyDao;

    @Before
    public void setup() throws SQLException {
        mysqlServiceRegistyDao = new MySqlServiceRegistryDaoImpl();
    }

    @Test
    public void testRollback() throws SQLException {
        List<Service> services = List.of(new Service("order-service-1", "localhost", "healthCheck"),
                new Service("payment-service", "localhost", "healthCheck"),
                new Service("order-service-1", "localhost", "healthCheck"));
        Assert.assertFalse(mysqlServiceRegistyDao.registerService(services));
        RegisteredService registeredService = mysqlServiceRegistyDao.queryServiceIp("order-service-1");
        Assert.assertNull(registeredService);
    }

    @Test
    public void testPersistence() throws SQLException {
        List<Service> services = List.of(new Service("order-service-1", "localhost", "healthCheck"),
                new Service("payment-service", "localhost", "healthCheck"),
                new Service("order-service-2", "localhost", "healthCheck"));
        mysqlServiceRegistyDao.registerService(services);
        RegisteredService registeredService = mysqlServiceRegistyDao.queryServiceIp("order-service-2");
        Assert.assertEquals("order-service-2", registeredService.getServiceName());
        Assert.assertEquals("localhost", registeredService.getUrl());
        Assert.assertEquals(null, registeredService.getUptime());
    }

    @After
    public void cleanUpDB() throws SQLException {
        mysqlServiceRegistyDao.removeService("order-service-1");
        mysqlServiceRegistyDao.removeService("payment-service");
        mysqlServiceRegistyDao.removeService("order-service-2");
    }
}
