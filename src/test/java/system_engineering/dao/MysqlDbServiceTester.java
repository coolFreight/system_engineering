package system_engineering.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import system_engineering.model.RegisteredService;
import system_engineering.model.ServiceRegistryInput;

import java.util.List;
public class MysqlDbServiceTester {
    private ServiceRegistryDao mysqlServiceRegistyDao;

    @Before
    public void setup() {
        mysqlServiceRegistyDao =  new MySqlServiceRegistryDao();
        mysqlServiceRegistyDao.deregisterService("order-service-1");
        mysqlServiceRegistyDao.deregisterService("payment-service");
        mysqlServiceRegistyDao.deregisterService("order-service-2");
    }

    @Test
    public void testRollback() {
        List<ServiceRegistryInput> services = List.of(new ServiceRegistryInput("order-service-1", "localhost"),
                new ServiceRegistryInput("payment-service", "localhost"),
                new ServiceRegistryInput("order-service-1", "localhost"));
        mysqlServiceRegistyDao.registerService(services);
        RegisteredService registeredService = mysqlServiceRegistyDao.queryServiceIp("order-service-1");
        Assert.assertNull(registeredService);
    }

    @Test
    public void testPersistence() {
        List<ServiceRegistryInput> services = List.of(new ServiceRegistryInput("order-service-1", "localhost"),
                new ServiceRegistryInput("payment-service", "localhost"),
                new ServiceRegistryInput("order-service-2", "localhost"));
        mysqlServiceRegistyDao.registerService(services);
        RegisteredService registeredService = mysqlServiceRegistyDao.queryServiceIp("order-service-2");
        Assert.assertEquals("order-service-2", registeredService.getServiceName());
        Assert.assertEquals("localhost", registeredService.getIp());
        Assert.assertEquals(null, registeredService.getUptime());
    }

    @After
    public void cleanUpDB(){
        mysqlServiceRegistyDao.deregisterService("order-service-1");
        mysqlServiceRegistyDao.deregisterService("payment-service");
        mysqlServiceRegistyDao.deregisterService("order-service-2");
    }

}
