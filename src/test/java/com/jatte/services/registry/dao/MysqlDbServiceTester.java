package com.jatte.services.registry.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.jatte.services.registry.model.RegisteredService;
import com.jatte.services.registry.model.Service;

import java.sql.SQLException;
import java.util.List;
public class MysqlDbServiceTester {
    private ServiceRegistryDao mysqlServiceRegistyDao;

    @Before
    public void setup() {
        mysqlServiceRegistyDao =  new MySqlServiceRegistryDaoImpl();
    }

    @Test
    public void testRollback() throws SQLException {
        List<Service> services = List.of(new Service("order-service-1", "localhost"),
                new Service("payment-service", "localhost"),
                new Service("order-service-1", "localhost"));
        Assert.assertFalse(mysqlServiceRegistyDao.registerService(services));
        RegisteredService registeredService = mysqlServiceRegistyDao.queryServiceIp("order-service-1");
        Assert.assertNull(registeredService);
    }

    @Test
    public void testPersistence() throws SQLException {
        List<Service> services = List.of(new Service("order-service-1", "localhost"),
                new Service("payment-service", "localhost"),
                new Service("order-service-2", "localhost"));
        mysqlServiceRegistyDao.registerService(services);
        RegisteredService registeredService = mysqlServiceRegistyDao.queryServiceIp("order-service-2");
        Assert.assertEquals("order-service-2", registeredService.getServiceName());
        Assert.assertEquals("localhost", registeredService.getIp());
        Assert.assertEquals(null, registeredService.getUptime());
    }

    @After
    public void cleanUpDB() throws SQLException {
        mysqlServiceRegistyDao.deregisterService("order-service-1");
        mysqlServiceRegistyDao.deregisterService("payment-service");
        mysqlServiceRegistyDao.deregisterService("order-service-2");
    }

}
