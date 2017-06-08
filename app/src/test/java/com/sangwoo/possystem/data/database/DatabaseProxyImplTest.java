package com.sangwoo.possystem.data.database;

import com.sangwoo.possystem.domain.database.DatabaseProxy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DatabaseProxyImplTest {

    private static final String HOST = "192.168.56.101";

    private DatabaseProxy database;

    @Test
    public void dbInit() throws Exception {
        login();
        Throwable throwable = database.dbInit().blockingGet();
        assertNull(throwable);
    }

    @Test
    public void failedLogin() throws Exception {
        String user = "dfdf";
        String password = "dfdf";
        database = new DatabaseProxyImpl();

        Throwable throwable = database.login(HOST, user, password).blockingGet();

        assertNotNull(throwable);
    }

    private void login() {
        String user = "possystem";
        String password = "possystem";
        database = new DatabaseProxyImpl();

        Throwable throwable = database.login(HOST, user, password).blockingGet();

        assertNull(throwable);
    }
}