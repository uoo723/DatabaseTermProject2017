package com.sangwoo.possystem.domain.usecase;

import com.sangwoo.possystem.domain.executor.PostExecutionThread;
import com.sangwoo.possystem.domain.executor.ThreadExecutor;
import com.sangwoo.possystem.domain.model.Employee;
import com.sangwoo.possystem.domain.repository.DataSource;
import io.reactivex.Observable;

import javax.inject.Inject;

public class RegisterEmployee extends UseCase<Employee, Object> {
    private final DataSource dataSource;

    @Inject
    RegisterEmployee(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                     DataSource dataSource) {
        super(threadExecutor, postExecutionThread);
        this.dataSource = dataSource;
    }

    @Override
    Observable<Object> buildUseCaseObservable(Employee employee) {
        return dataSource.createEmployee(employee).toObservable();
    }
}
