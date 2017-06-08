package com.sangwoo.possystem.domain.usecase;

import com.sangwoo.possystem.domain.executor.PostExecutionThread;
import com.sangwoo.possystem.domain.executor.ThreadExecutor;
import com.sangwoo.possystem.domain.model.Menu;
import com.sangwoo.possystem.domain.repository.DataSource;
import io.reactivex.Observable;

import javax.inject.Inject;

public class CreateMenu extends UseCase<Menu, Object> {
    private final DataSource dataSource;

    @Inject
    CreateMenu(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
               DataSource dataSource) {
        super(threadExecutor, postExecutionThread);
        this.dataSource = dataSource;
    }

    @Override
    Observable<Object> buildUseCaseObservable(Menu menu) {
        return dataSource.createMenu(menu).toObservable();
    }
}
