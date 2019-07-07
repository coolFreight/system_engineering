package system_engineering.dao;

import system_engineering.model.RegisteredService;
import system_engineering.model.ServiceRegistryInput;

import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class MySqlServiceRegistryDao implements ServiceRegistryDao {

    private Connection conn;
    private PreparedStatement queryRegisteredService = null;
    private PreparedStatement getService = null;
    private static final String DB_NAME = "system_engineering";
    private String insertServiceSql = "insert into " + DB_NAME + ".service_registry (service, ip) values (?,?)";
    private String removeServiceSql = "delete from " + DB_NAME + ".service_registry where service = ?";
    private String getServiceNameSql = "select * from " + DB_NAME + ".service_registry where service = ?";

    public MySqlServiceRegistryDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties properties = new Properties();
            properties.put("user", "serialcoderer");
            properties.put("password", "");
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
    public boolean registerService(String serviceName, String ip) {
        try {
            conn.setAutoCommit(false);
            queryRegisteredService = conn.prepareStatement(insertServiceSql);
            queryRegisteredService.setString(1, serviceName);
            queryRegisteredService.setString(2, ip);
            queryRegisteredService.executeUpdate();
            conn.commit();
            return true;
        } catch (SQLException sqle) {
            System.err.println("There was an error inserting service into table " + sqle.getMessage());
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean registerService(List<ServiceRegistryInput> registryInputs) {
        try {
            conn.setAutoCommit(false);
            for (ServiceRegistryInput registryInput : registryInputs) {
                queryRegisteredService = conn.prepareStatement(insertServiceSql);
                queryRegisteredService.setString(1, registryInput.getService());
                queryRegisteredService.setString(2, registryInput.getIp());
                queryRegisteredService.executeUpdate();
            }
            conn.commit();
            return true;
        } catch (SQLException sqle) {
            System.err.println("There was an error inserting service into table " + sqle.getMessage());
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deRregisterService(List<ServiceRegistryInput> registryInputs) {
        return false;
    }

    @Override
    public boolean deregisterService(String serviceName) {

        try {
            conn.setAutoCommit(false);
            queryRegisteredService = conn.prepareStatement(removeServiceSql);
            queryRegisteredService.setString(1, serviceName);
            queryRegisteredService.executeUpdate();
            conn.commit();
            return true;
        } catch (SQLException sqle) {
            System.err.println("There was an error deregistering service " + serviceName + " from table " + DB_NAME + " " + sqle.getMessage());
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public RegisteredService queryServiceIp(String serviceName) {
        RegisteredService registeredService = null;
        try {
            queryRegisteredService = conn.prepareStatement(getServiceNameSql);
            queryRegisteredService.setString(1, serviceName);
            ResultSet result = queryRegisteredService.executeQuery();
            result.next();
            registeredService = new RegisteredService();
            registeredService.setServiceName(result.getString("service"));
            registeredService.setIp(result.getString("ip"));
            System.out.println("Retrieved service [ " + registeredService + " ]");
        } catch (SQLException sqle) {
            System.err.println("There was an error querying service into table " + sqle.getMessage());
            sqle.printStackTrace();
        }
        return registeredService;
    }
}
