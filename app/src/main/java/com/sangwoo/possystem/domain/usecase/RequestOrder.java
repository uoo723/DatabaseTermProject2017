package com.sangwoo.possystem.domain.usecase;

import com.sangwoo.possystem.domain.executor.PostExecutionThread;
import com.sangwoo.possystem.domain.executor.ThreadExecutor;
import com.sangwoo.possystem.domain.model.Order;
import com.sangwoo.possystem.domain.repository.DataSource;
import io.reactivex.Observable;

import javax.inject.Inject;

public class RequestOrder extends UseCase<Order, Object> {
    private final DataSource dataSource;

    @Inject
    RequestOrder(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                 DataSource dataSource) {
        super(threadExecutor, postExecutionThread);
        this.dataSource = dataSource;
    }

    @Override
    Observable<Object> buildUseCaseObservable(Order order) {
        return dataSource.createOrder(order).toObservable();
    }
}
