package com.sangwoo.possystem.domain.database;

import io.reactivex.Completable;

public interface DatabaseProxy {
    Completable dbInit();
    Completable login(String host, String user, String password);
}
