package system_engineering;
//
//import com.github.junit5docker.Docker;
//import com.github.junit5docker.Environment;
//import com.github.junit5docker.Port;
//import com.github.junit5docker.WaitFor;
//import org.junit.jupiter.api.Test;
//
//@Docker(image = "mysql", ports = @Port(exposed = 8801, inner = 3306),
//        environments = {
//                @Environment(key = "MYSQL_ROOT_PASSWORD", value = "root"),
//                @Environment(key = "MYSQL_DATABASE", value = "testdb"),
//                @Environment(key = "MYSQL_USER", value = "testuser"),
//                @Environment(key = "MYSQL_PASSWORD", value = "s3cr3t"),
//        },
//        waitFor = @WaitFor("mysqld: ready for connections"))
//public class PostgresConceptsTest {
//
//
//    @Test
//    void test() {
//
//        System.out.println("hello");
//    }
//}
