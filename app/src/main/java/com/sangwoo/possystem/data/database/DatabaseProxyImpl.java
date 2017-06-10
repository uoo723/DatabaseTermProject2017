package com.sangwoo.possystem.data.database;

import com.sangwoo.possystem.common.utils.ScriptRunner;
import com.sangwoo.possystem.domain.database.DatabaseProxy;
import com.sangwoo.possystem.domain.model.Table;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.davidmoten.rx.jdbc.Database;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class DatabaseProxyImpl implements DatabaseProxy {
    private static final Logger logger = LogManager.getLogger();

    private static final String DRIVER = "oracle.jdbc.OracleDriver";
    private static final String _URL = "jdbc:oracle:thin:@%s:1521:XE";

    private Database database;

    @Inject
    public DatabaseProxyImpl() {}

    @SuppressWarnings("ConstantConditions")
    @Override
    public Completable dbInit() {
        return Completable.fromAction(() -> {
            logger.info("dbInit");

            URL resource = DatabaseProxyImpl.class.getClassLoader().getResource("create.sql");

            if (database == null) {
                logger.warn("login first");
                throw new Exception("Login first");
            }

            Connection conn = database.connections().blockingFirst();
            ScriptRunner runner = new ScriptRunner(conn);

            Reader reader = new BufferedReader(new FileReader(resource.getFile()));

            runner.setSendFullScript(true);
            runner.runScript(reader);

            long count = database.select("select * from \"TABLE\"").count().blockingGet();
            logger.debug("count: " + count);
            if (count == 0) {
                insertTables();
            }
        });
    }

    @Override
    public Completable login(String host, String user, String password) {
        createDatabase(host, user, password);
        return database.select("select 1 from dual").count().toCompletable();
    }

    public Database getDatabase() {
        return database;
    }

    private void createDatabase(final String host, final String user, final String password) {
        Flowable<Connection> f = Flowable.fromCallable(() -> {
            Class.forName(DRIVER);
            return DriverManager.getConnection(String.format(_URL, host), user, password);
        });

        database = Database.from(f, () -> {});
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    private void insertTables() {
        logger.debug("insertTables");
        for (int i = 1; i <= 20; i++) {
            database.update("insert into \"TABLE\"(table_num) values(?)")
                    .parameters(i)
                    .complete()
                    .blockingGet();
        }
    }
}
