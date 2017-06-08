package com.sangwoo.possystem.domain.usecase;


import com.sangwoo.possystem.domain.executor.PostExecutionThread;
import com.sangwoo.possystem.domain.executor.ThreadExecutor;
import com.sangwoo.possystem.domain.model.Customer;
import com.sangwoo.possystem.domain.repository.DataSource;
import io.reactivex.Observable;

import javax.inject.Inject;

public class RegisterCustomer extends UseCase<Customer, Object> {
    private final DataSource dataSource;

    @Inject
    RegisterCustomer(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                     DataSource dataSource) {
        super(threadExecutor, postExecutionThread);
        this.dataSource = dataSource;
    }

    @Override
    Observable<Object> buildUseCaseObservable(Customer customer) {
        return dataSource.createCustomer(customer).toObservable();
    }
}
