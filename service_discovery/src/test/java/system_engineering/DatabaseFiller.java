package system_engineering;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.postgresql.ds.PGSimpleDataSource;

import java.nio.charset.Charset;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Random;
import java.util.UUID;

public class DatabaseFiller {

    private Jdbi jdbi;
    private QueryPracticeRepository dao;
    private static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;


    void connect() throws SQLException {

        PGSimpleDataSource source = new PGSimpleDataSource();
        source.setServerName("localhost");
        int[] ports = new int[2];
        ports[0] = 6000;
        source.setPortNumbers(ports);
        source.setDatabaseName("query_practice");
        source.setUser("postgres");
        source.setPassword("mysecretpassword");


        dao = Jdbi.create(source)
                .installPlugin(new PostgresPlugin())
                .installPlugin(new SqlObjectPlugin())
                .registerRowMapper(new TestUserMapper())
                .onDemand(QueryPracticeRepository.class);

//        String url = "jdbc:postgresql://localhost:6000/query_practice";
//        Properties props = new Properties();
//        props.setProperty("user","postgres");
//        props.setProperty("password","mysecretpassword");
//        Connection conn = DriverManager.getConnection(url, props);
//        dao = jdbi

    }

    QueryPracticeRepository getQueryPracticeRepo() {
        return dao;
    }


    public static void main(String[] args) throws SQLException, JsonProcessingException {
        DatabaseFiller dbf = new DatabaseFiller();
        dbf.connect();
        var dao = dbf.getQueryPracticeRepo();

        new Thread( () -> {
            try {
                backfill(dao);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread( () -> {
            try {
                backfill(dao);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread( () -> {
            try {
                backfill(dao);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread( () -> {
            try {
                backfill(dao);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread( () -> {
            try {
                backfill(dao);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }).start();


    }

    private static void backfill(QueryPracticeRepository dao) throws JsonProcessingException {
        long count_members = 250_000;
        HashSet<TestUser> users_to_persist = new HashSet<>();


        while(count_members > 0) {
            Random random = new Random();
            int minDay = (int) LocalDate.of(1965, 1, 1).toEpochDay();
            int maxDay = (int) LocalDate.of(2015, 1, 1).toEpochDay();
            long randomDay = minDay + random.nextInt(maxDay - minDay);

            LocalDate randomBirthDate = LocalDate.parse(LocalDate.ofEpochDay(randomDay).format(DateTimeFormatter.ISO_DATE));


            int leftLimit = 97; // letter 'a'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 10;
            Random randomName = new Random();

            String generatedString = randomName.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();


            var id = UUID.randomUUID().toString();
            var name = new Name(generatedString, generatedString, generatedString, randomBirthDate.format(DateTimeFormatter.ISO_DATE));
            var objectMapper = new ObjectMapper();
            var nameSerialized = objectMapper.writeValueAsString(name);
            users_to_persist.add(new TestUser(id, nameSerialized, "ADVANCED", getRandomEventDay(random)));
            users_to_persist.add(new TestUser(id, nameSerialized, "BASIC", getRandomEventDay(random)));
            count_members--;

            if(users_to_persist.size() >= 10_000) {
                users_to_persist.forEach(u -> dao.addUser(u));
                System.out.println("This how many members left "+count_members);
                users_to_persist.clear();
            }
        }
    }

    private static Instant getRandomEventDay(Random random) {
        int minEventDay = (int) LocalDateTime.of(2010, 1, 1, 1, 0).toEpochSecond(ZoneOffset.UTC);
        int maxEventDay = (int) LocalDateTime.of(2021, 9, 1, 1, 0).toEpochSecond(ZoneOffset.UTC);
        long randomEventDay = minEventDay + random.nextInt(maxEventDay - minEventDay);
        return Instant.ofEpochSecond(randomEventDay);
    }
}
