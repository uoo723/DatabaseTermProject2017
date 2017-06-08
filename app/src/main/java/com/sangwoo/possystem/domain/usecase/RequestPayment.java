package com.sangwoo.possystem.domain.usecase;

import com.sangwoo.possystem.domain.executor.PostExecutionThread;
import com.sangwoo.possystem.domain.executor.ThreadExecutor;
import com.sangwoo.possystem.domain.model.Payment;
import com.sangwoo.possystem.domain.model.Table;
import com.sangwoo.possystem.domain.repository.DataSource;
import io.reactivex.Observable;

import javax.inject.Inject;

public class RequestPayment extends UseCase<Payment, Object> {
    private final DataSource dataSource;

    @Inject
    RequestPayment(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                   DataSource dataSource) {
        super(threadExecutor, postExecutionThread);
        this.dataSource = dataSource;
    }

    @Override
    Observable<Object> buildUseCaseObservable(Payment payment) {
        Table table = payment.getOrder().getTable();
        table.setPaymentCompleted(true);
        return Observable.merge(dataSource.createPayment(payment).toObservable(),
                dataSource.updateTable(table).toObservable());
    }
}
