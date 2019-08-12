package tinyurl.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tinyurl.service.model.TinyUrlClickedMetaData;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

/**
 * This would contain the database implementation.
 */
public class TinyUrlDBRepository implements UrlRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(TinyUrlDBRepository.class);

    private static BasicDataSource ds = new BasicDataSource();
    private String DB_NAME;
    private PreparedStatement preparedStatement = null;
    private String INSERT_LONG_URL;


    public TinyUrlDBRepository() throws Exception {
        try (FileInputStream fis = new FileInputStream("url-service/src/main/resources/tinyurl.db.properties")) {
            Properties properties = new Properties();
            properties.load(fis);
            ds.setUrl(properties.getProperty("db.url"));
            ds.setUsername(properties.getProperty("db.username"));
            ds.setPassword(properties.getProperty("db.password"));
            ds.setMinIdle(Integer.valueOf(properties.getProperty("db.minIdle")));
            ds.setMaxIdle(Integer.valueOf(properties.getProperty("db.maxIdle")));
            DB_NAME = properties.getProperty("db.schema");
            INSERT_LONG_URL = "insert into " + DB_NAME + ".tinyurl (original_url) values (?) ";
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Could not initialize database");
        }
    }

    @Override
    public long persistLongUrl(String longUrl) {
        long primaryKey = -1;
        try (Connection conn = ds.getConnection()) {
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(INSERT_LONG_URL);
            preparedStatement.setString(1, longUrl);
            preparedStatement.execute();
            preparedStatement = conn.prepareStatement("SELECT LAST_INSERT_ID()");
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            primaryKey = rs.getLong("LAST_INSERT_ID()");
            conn.commit();
            LOGGER.info("Inserted long url {} with primary key {} ", longUrl, primaryKey);
        } catch (SQLException e) {
            LOGGER.error("Could not insert long url {} into table", longUrl, e);
        } finally {
            return primaryKey;
        }
    }

    @Override
    public boolean persistTinyUrl(Long primaryKey, String tinyUrl) {
        return false;
    }

    @Override
    public Optional<String> getLongUrl(Long primaryKey) {
        return Optional.empty();
    }

    @Override
    public boolean isLongUrlPersisted(String longUrl) {
        return false;
    }

    @Override
    public Optional<String> getTinyUrl(String longUrl) {
        return null;
    }

    @Override
    public boolean updateTinyUrlMetaData(Long primaryKey, TinyUrlClickedMetaData metaData) {
        return false;
    }
}
