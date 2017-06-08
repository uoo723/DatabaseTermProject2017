package com.sangwoo.possystem.domain.usecase;

import com.sangwoo.possystem.domain.executor.PostExecutionThread;
import com.sangwoo.possystem.domain.executor.ThreadExecutor;
import com.sangwoo.possystem.domain.model.Order;
import com.sangwoo.possystem.domain.model.Table;
import com.sangwoo.possystem.domain.repository.DataSource;
import io.reactivex.Observable;

import javax.inject.Inject;

public class CancelOrder extends UseCase<Order, Object> {
    private final DataSource dataSource;

    @Inject
    CancelOrder(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                DataSource dataSource) {
        super(threadExecutor, postExecutionThread);
        this.dataSource = dataSource;
    }

    @Override
    Observable<Object> buildUseCaseObservable(Order order) {
        Table table = order.getTable();
        table.setPaymentCompleted(true);

        return Observable.merge(dataSource.deleteOrder(table.getId()).toObservable(),
                dataSource.updateTable(table).toObservable());
    }
}
