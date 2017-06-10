package com.sangwoo.possystem.domain.usecase;

import com.sangwoo.possystem.domain.executor.PostExecutionThread;
import com.sangwoo.possystem.domain.executor.ThreadExecutor;
import com.sangwoo.possystem.domain.repository.DataSource;
import io.reactivex.Completable;
import io.reactivex.Observable;

import javax.inject.Inject;
import java.io.File;
import java.util.Scanner;

public class LoadData extends UseCase<File, Object> {

    private final DataSource dataSource;

    @Inject
    LoadData(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
             DataSource dataSource) {
        super(threadExecutor, postExecutionThread);
        this.dataSource = dataSource;
    }

    @Override
    Observable<Object> buildUseCaseObservable(File file) {
        return Completable.fromAction(() -> loadData(file)).toObservable();
    }

    private void loadData(File file) throws Exception {
        Scanner sc = new Scanner(file);
        int count = sc.nextInt();

        for (int i = 0; i < count; i++) {

        }
    }
}
