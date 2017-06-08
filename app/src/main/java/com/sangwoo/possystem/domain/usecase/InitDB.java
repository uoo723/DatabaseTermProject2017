package com.sangwoo.possystem.domain.usecase;

import com.sangwoo.possystem.domain.database.DatabaseProxy;
import com.sangwoo.possystem.domain.executor.PostExecutionThread;
import com.sangwoo.possystem.domain.executor.ThreadExecutor;
import io.reactivex.Observable;

import javax.inject.Inject;

public class InitDB extends UseCase<Void, Object> {
    private final DatabaseProxy database;

    @Inject
    InitDB(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
           DatabaseProxy database) {
        super(threadExecutor, postExecutionThread);
        this.database = database;
    }

    @Override
    Observable<Object> buildUseCaseObservable(Void aVoid) {
        return database.dbInit().toObservable();
    }
}
