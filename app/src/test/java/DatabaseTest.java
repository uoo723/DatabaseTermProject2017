import com.sangwoo.possystem.common.utils.ScriptRunner;
import io.reactivex.Flowable;
import org.davidmoten.rx.jdbc.Database;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DatabaseTest {

    private static final String DRIVER = "oracle.jdbc.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@%s:1521:XE";

    private String host = "192.168.56.101";
    private String user = "possystem";
    private String password = "possystem";

    @Test
    public void connectionTest() throws Exception {
        Flowable<Connection> f = Flowable.fromCallable(() -> {
            Class.forName(DRIVER);
            return DriverManager.getConnection(String.format(URL, host), user, password);
        });

        Database d = Database.from(f, () -> {});
        long count = d.select("select 1 from dual").count().blockingGet();
        assertEquals(count, 1);
    }

    @Test
    public void scriptRunnerTest() throws Exception {
        URL resource = DatabaseTest.class.getResource("create.sql");
        Class.forName(DRIVER);
        Connection conn = DriverManager.getConnection(String.format(URL, host), user, password);

        ScriptRunner runner = new ScriptRunner(conn);

        Reader reader = new BufferedReader(new FileReader(resource.getFile()));

        System.out.println("path: " + resource.getPath());
        runner.setSendFullScript(true);
        runner.runScript(reader);

        Throwable throwable = null;
        try {
            conn.prepareStatement("select * from payment").execute();
        } catch (SQLException e) {
            throwable = e;
        }

        assertNull(throwable);
    }
}
