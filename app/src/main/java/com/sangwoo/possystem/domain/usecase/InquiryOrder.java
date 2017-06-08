package com.sangwoo.possystem.domain.usecase;


import com.sangwoo.possystem.domain.executor.PostExecutionThread;
import com.sangwoo.possystem.domain.executor.ThreadExecutor;
import com.sangwoo.possystem.domain.model.Order;
import com.sangwoo.possystem.domain.repository.DataSource;
import io.reactivex.Observable;

import javax.inject.Inject;

public class InquiryOrder extends UseCase<Integer, Order> {
    private final DataSource dataSource;

    @Inject
    InquiryOrder(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                 DataSource dataSource) {
        super(threadExecutor, postExecutionThread);
        this.dataSource = dataSource;
    }

    @Override
    Observable<Order> buildUseCaseObservable(Integer tableId) {
        return dataSource.getOrder(tableId).toObservable();
    }
}
