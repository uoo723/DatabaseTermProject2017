package com.sangwoo.possystem.domain.usecase;

import com.sangwoo.possystem.domain.executor.PostExecutionThread;
import com.sangwoo.possystem.domain.executor.ThreadExecutor;
import com.sangwoo.possystem.domain.model.Table;
import com.sangwoo.possystem.domain.repository.DataSource;
import io.reactivex.Observable;

import javax.inject.Inject;
import java.util.List;

public class GetTables extends UseCase<Void, List<Table>> {
    private final DataSource dataSource;

    @Inject
    GetTables(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
              DataSource dataSource) {
        super(threadExecutor, postExecutionThread);
        this.dataSource = dataSource;
    }

    @Override
    Observable<List<Table>> buildUseCaseObservable(Void aVoid) {
        return dataSource.getTables().toObservable();
    }
}
