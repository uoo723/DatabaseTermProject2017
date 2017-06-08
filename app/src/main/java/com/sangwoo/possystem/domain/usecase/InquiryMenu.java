package com.sangwoo.possystem.domain.usecase;

import com.sangwoo.possystem.domain.executor.PostExecutionThread;
import com.sangwoo.possystem.domain.executor.ThreadExecutor;
import com.sangwoo.possystem.domain.model.Menu;
import com.sangwoo.possystem.domain.repository.DataSource;
import io.reactivex.Observable;

import javax.inject.Inject;

public class InquiryMenu extends UseCase<String, Menu> {
    private final DataSource dataSource;

    @Inject
    InquiryMenu(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                DataSource dataSource) {
        super(threadExecutor, postExecutionThread);
        this.dataSource = dataSource;
    }

    @Override
    Observable<Menu> buildUseCaseObservable(String menuName) {
        return dataSource.getMenu(menuName).toObservable();
    }
}
