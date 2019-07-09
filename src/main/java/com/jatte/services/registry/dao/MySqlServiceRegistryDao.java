package com.jatte.services.registry.dao;

import com.jatte.services.registry.model.RegisteredService;
import com.jatte.services.registry.model.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;
import java.util.Properties;

public class MySqlServiceRegistryDao implements ServiceRegistryDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(MySqlServiceRegistryDao.class);

    private Connection conn;
    private PreparedStatement queryRegisteredService = null;
    private static final String DB_NAME = "system_engineering";
    private String insertServiceSql = "insert into " + DB_NAME + ".service_registry (service, ip) values (?,?)";
    private String removeServiceSql = "delete from " + DB_NAME + ".service_registry where service = ?";
    private String getServiceNameSql = "select * from " + DB_NAME + ".service_registry where service = ?";

    public MySqlServiceRegistryDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties properties = new Properties();
            properties.put("user", "serialcoderer");
            properties.put("password", "rainbow14");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", properties);
        } catch (SQLException sqle) {
            System.err.println("There was an error getting a connection to db " + sqle.getMessage());
            sqle.printStackTrace();
            throw new RuntimeException("Could not connect to DB");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean registerService(Service service) {
        try {
            conn.setAutoCommit(false);
            queryRegisteredService = conn.prepareStatement(insertServiceSql);
            queryRegisteredService.setString(1, service.getServiceName());
            queryRegisteredService.setString(2, service.getIp());
            queryRegisteredService.executeUpdate();
            conn.commit();
            return true;
        } catch (SQLException sqle) {
            System.err.println("There was an error inserting service into table " + sqle.getMessage());
            sqle.printStackTrace();
            try {
                LOGGER.warn("Transaction was rollback");
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
            for (Service registryInput : registryInputs) {
                conn.setAutoCommit(false);
                queryRegisteredService = conn.prepareStatement(insertServiceSql);
                queryRegisteredService.setString(1, registryInput.getServiceName());
                queryRegisteredService.setString(2, registryInput.getIp());
                queryRegisteredService.executeUpdate();
            }
            conn.commit();
            return true;
        } catch (SQLException sqle) {
            LOGGER.error("There was an error inserting service into table ", sqle);
        }
        if (!successfulTransaction) {
            LOGGER.warn("Transaction was rolled back");
            conn.rollback();
        }
        return successfulTransaction;
    }

    @Override
    public boolean deRregisterService(List<String> serviceName) {
        return false;
    }

    @Override
    public boolean deregisterService(String serviceName) throws SQLException {
        boolean successfulTransaction = false;
        try {
            conn.setAutoCommit(false);
            queryRegisteredService = conn.prepareStatement(removeServiceSql);
            queryRegisteredService.setString(1, serviceName);
            queryRegisteredService.executeUpdate();
            conn.commit();
            successfulTransaction = true;
        } catch (SQLException sqle) {
            LOGGER.error("There was an error de-registering service {}  from table {} ", serviceName, DB_NAME, sqle);
            sqle.printStackTrace();
        }
        if (!successfulTransaction) {
            conn.rollback();
        }
        return successfulTransaction;
    }

    @Override
    public RegisteredService queryServiceIp(String serviceName) {
        RegisteredService registeredService = null;
        try {
            queryRegisteredService = conn.prepareStatement(getServiceNameSql);
            queryRegisteredService.setString(1, serviceName);
            ResultSet result = queryRegisteredService.executeQuery();
            if (result.next()) {
                registeredService = new RegisteredService();
                registeredService.setServiceName(result.getString("service"));
                registeredService.setIp(result.getString("ip"));
                result.close();
            }
            LOGGER.info("Retrieved registered service [ {} ]", registeredService);
        } catch (SQLException sqle) {
            LOGGER.error("There was an error querying service into table ", sqle);
        }
        return registeredService;
    }
}
