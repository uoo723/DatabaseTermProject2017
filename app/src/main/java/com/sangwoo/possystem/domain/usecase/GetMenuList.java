package com.sangwoo.possystem.domain.usecase;

import com.sangwoo.possystem.domain.executor.PostExecutionThread;
import com.sangwoo.possystem.domain.executor.ThreadExecutor;
import com.sangwoo.possystem.domain.model.Menu;
import com.sangwoo.possystem.domain.repository.DataSource;
import io.reactivex.Observable;

import javax.inject.Inject;
import java.util.List;

public class GetMenuList extends UseCase<Void, List<Menu>> {

    private final DataSource dataSource;

    @Inject
    GetMenuList(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, DataSource dataSource) {
        super(threadExecutor, postExecutionThread);
        this.dataSource = dataSource;
    }

    @Override
    Observable<List<Menu>> buildUseCaseObservable(Void aVoid) {
        return dataSource.getMenus().toObservable();
    }
}
