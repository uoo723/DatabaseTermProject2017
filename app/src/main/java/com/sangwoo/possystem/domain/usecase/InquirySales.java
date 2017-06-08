package com.sangwoo.possystem.domain.usecase;

import com.sangwoo.possystem.domain.executor.PostExecutionThread;
import com.sangwoo.possystem.domain.executor.ThreadExecutor;
import com.sangwoo.possystem.domain.model.Sales;
import com.sangwoo.possystem.domain.repository.DataSource;
import io.reactivex.Observable;

import javax.inject.Inject;
import java.util.Date;

public class InquirySales extends UseCase<Date, Sales> {
    private final DataSource dataSource;

    @Inject
    InquirySales(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                 DataSource dataSource) {
        super(threadExecutor, postExecutionThread);
        this.dataSource = dataSource;
    }

    @Override
    Observable<Sales> buildUseCaseObservable(Date date) {
        return dataSource.getSales(date).toObservable();
    }
}
