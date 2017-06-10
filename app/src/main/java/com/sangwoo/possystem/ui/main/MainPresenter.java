package com.sangwoo.possystem.ui.main;

import com.sangwoo.possystem.domain.usecase.InquiryEmployee;
import com.sangwoo.possystem.domain.usecase.LoadData;
import com.sangwoo.possystem.ui.EmployeeLoginSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.io.File;

public class MainPresenter implements MainContract.Presenter {
    private static final Logger logger = LogManager.getLogger();

    private MainContract.View view;

    private final InquiryEmployee inquiryEmployeeUseCase;
    private final LoadData loadDataUseCase;

    @Inject
    MainPresenter(InquiryEmployee inquiryEmployee, LoadData loadData) {
        inquiryEmployeeUseCase = inquiryEmployee;
        loadDataUseCase = loadData;
    }

    @Override
    public void bindView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void loadData(File file) {
        loadDataUseCase.execute(file, o -> {},
                throwable -> {
//                    throwable.printStackTrace();
                    view.failedLoadingData();
                },
                view::succeedLoadingData);
    }

    @Override
    public void employeeLogin(String name, String employeeId) {
        inquiryEmployeeUseCase.execute(name, employee -> {
            if (employee.getEmployeeId().equals(employeeId)) {
                view.succeedEmployeeLogin();
                EmployeeLoginSession.login(employee);
            } else {
                view.failedEmployeeLogin();
            }
        }, throwable -> {
//            throwable.printStackTrace();
            view.failedEmployeeLogin();
        });
    }
}
