package system_engineering;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class TestContainerPostgres {

    @Container
    private static GenericContainer postgresqlContainer = new PostgreSQLContainer("postgres:concepts-postrgres-docker")
            .withDatabaseName("product_db")
            .withUsername("user")
            .withPassword("password");


    @BeforeAll
    static void init() {
//        var config = new HikariConfig();
//        var jdbcContainer = (JdbcDatabaseContainer<?>) postgresqlContainer;
//        config.setJdbcUrl(jdbcContainer.getJdbcUrl());
//        config.setUsername(jdbcContainer.getUsername());
//        config.setPassword(jdbcContainer.getPassword());
//        config.setDriverClassName(jdbcContainer.getDriverClassName());
//        datasource = new HikariDataSource(config);
    }



    @Test
    void test() {

        assertEquals(postgresqlContainer.isRunning(), true);
    }
}
