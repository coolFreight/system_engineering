package com.jatte.services.service_discovery.dao;

import com.jatte.services.service_discovery.model.RegisteredService;
import com.jatte.services.service_discovery.model.Service;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlServiceRegistryDaoImpl implements ServiceRegistryDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(MySqlServiceRegistryDaoImpl.class);
    private static final String HEALTH_CHECK_URL_COLUMN = "health_check_url";
    private static final String RUNNING_SINCE_COLUMN = "running_since";
    private static final String URL_COLUMN = "url";
    private static final String SERVICE_COLUMN = "service";

    private Connection conn;
    private PreparedStatement preparedStatement = null;
    private static final String DB_NAME = "system_engineering";
    private String insertServiceSql = "insert into " + DB_NAME + ".service_registry (service, url, health_check_url) values (?,?,?)";
    private String deregisterService = "update " + DB_NAME + ".service_registry SET connected = 'f' where service= ?";
    private String getServiceNameSql = "select * from " + DB_NAME + ".service_registry where service = ?";
    private String allConnectedServicesSql = "select * from " + DB_NAME + ".service_registry where connected = 't'";
    private String removeServiceSql = "delete from " + DB_NAME + ".service_registry where service = ?";


    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl("jdbc:mysql://localhost:3306/");
        ds.setUsername("serialcoderer");
        ds.setPassword("rainbow14");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    @Override
    public boolean registerService(Service service) {
        LOGGER.info("Persisting service {} ", service.getServiceName());
        try {
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(insertServiceSql);
            preparedStatement.setString(1, service.getServiceName());
            preparedStatement.setString(2, service.getUrl());
            preparedStatement.setString(3, service.getHealthCheckUrl());
            preparedStatement.executeUpdate();
            conn.commit();
            conn.close();
            LOGGER.info("Persisted service {} ", service.getServiceName());
            return true;
        } catch (SQLException sqle) {
            LOGGER.error("Could not register service {} ", service, sqle);
            sqle.printStackTrace();
            try {
                LOGGER.warn("Transaction was rolled back");
                conn.rollback();
            } catch (SQLException e) {
                LOGGER.error("There was an error inserting service into table ", sqle);
            }
        }
        return false;
    }

    @Override
    public boolean registerService(List<Service> registryInputs) throws SQLException {
        boolean successfulTransaction = false;
        try {
            conn = ds.getConnection();
            for (Service registryInput : registryInputs) {
                conn.setAutoCommit(false);
                preparedStatement = conn.prepareStatement(insertServiceSql);
                preparedStatement.setString(1, registryInput.getServiceName());
                preparedStatement.setString(2, registryInput.getUrl());
                preparedStatement.setString(3, registryInput.getHealthCheckUrl());
                preparedStatement.executeUpdate();
            }
            conn.commit();
            return true;
        } catch (SQLException sqle) {
            LOGGER.error("There was an error inserting service into table ", sqle);
        } finally {
            if (!successfulTransaction) {
                LOGGER.warn("Transaction was rolled back");
                conn.rollback();
            }
            conn.close();
        }
        return successfulTransaction;
    }

    @Override
    public boolean deRregisterService(List<String> serviceName) {
        return false;
    }

    @Override
    public boolean deregisterService(String serviceName) throws SQLException {
        LOGGER.info("Evicting service={} ", serviceName);
        boolean successfulTransaction = false;
        try {
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(deregisterService);
            preparedStatement.setString(1, serviceName);
            preparedStatement.executeUpdate();
            conn.commit();
            successfulTransaction = true;
            LOGGER.info("Evicted service={} ", serviceName);
        } catch (SQLException sqle) {
            LOGGER.error("There was an error de-registering service {}  from table {} ", serviceName, DB_NAME, sqle);
        } finally {
            if (!successfulTransaction) {
                conn.rollback();
            }
            conn.close();
        }
        return successfulTransaction;
    }

    @Override
    public RegisteredService queryServiceIp(String serviceName) {
        RegisteredService registeredService = null;
        try {
            conn = ds.getConnection();
            preparedStatement = conn.prepareStatement(getServiceNameSql);
            preparedStatement.setString(1, serviceName);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                registeredService = new RegisteredService();
                registeredService.setServiceName(result.getString(SERVICE_COLUMN));
                registeredService.setUrl(result.getString(URL_COLUMN));
                registeredService.setHealthCheckUrl(result.getString(HEALTH_CHECK_URL_COLUMN));
                result.close();
            }
            conn.close();
            LOGGER.info("Retrieved registered service [ {} ]", registeredService);
        } catch (SQLException sqle) {
            LOGGER.error("There was an error querying service into table ", sqle);
        }
        return registeredService;
    }

    public List<RegisteredService> getRegisteredServices() {
        List<RegisteredService> registeredServices = new ArrayList<>();
        try {
            conn = ds.getConnection();
            preparedStatement = conn.prepareStatement(allConnectedServicesSql);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                RegisteredService registeredService = new RegisteredService();
                registeredService.setServiceName(result.getString(SERVICE_COLUMN));
                registeredService.setUrl(result.getString(URL_COLUMN));
                registeredService.setUptime(result.getTimestamp(RUNNING_SINCE_COLUMN));
                registeredService.setHealthCheckUrl(result.getString(HEALTH_CHECK_URL_COLUMN));
                registeredServices.add(registeredService);
                LOGGER.info("Retrieved registered service [ {} ]", registeredService);
            }
            result.close();
        } catch (SQLException sqle) {
            LOGGER.error("There was an error querying service into table ", sqle);
        }
        return registeredServices;
    }

    @Override
    public boolean removeService(String service) throws SQLException {
        boolean successfulTransaction = false;
        try {
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(removeServiceSql);
            preparedStatement.setString(1, service);
            preparedStatement.executeUpdate();
            conn.commit();
            successfulTransaction = true;
        } catch (SQLException sqle) {
            LOGGER.error("There was an error de-registering service {}  from table {} ", service, DB_NAME, sqle);
            sqle.printStackTrace();
        } finally {
            if (!successfulTransaction) {
                conn.rollback();
            }
            conn.close();
            return successfulTransaction;
        }

    }
}
