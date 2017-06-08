package com.sangwoo.possystem.domain.usecase;

import com.sangwoo.possystem.domain.database.DatabaseProxy;
import com.sangwoo.possystem.domain.executor.PostExecutionThread;
import com.sangwoo.possystem.domain.executor.ThreadExecutor;
import io.reactivex.Observable;

import javax.inject.Inject;

public class DBLogin extends UseCase<DBLogin.Params, Object> {

    private final DatabaseProxy database;

    @Inject
    DBLogin(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
            DatabaseProxy database) {
        super(threadExecutor, postExecutionThread);
        this.database = database;
    }

    @Override
    Observable<Object> buildUseCaseObservable(Params params) {
        return database.login(params.host, params.id, params.password).toObservable();
    }

    public static final class Params {
        private String host;
        private String id;
        private String password;

        public Params(String host, String id, String password) {
            this.host = host;
            this.id = id;
            this.password = password;
        }
    }
}
