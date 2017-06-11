package com.sangwoo.possystem.ui.main;

import com.sangwoo.possystem.domain.usecase.GetTables;
import com.sangwoo.possystem.domain.usecase.InquiryEmployee;
import com.sangwoo.possystem.domain.usecase.LoadData;
import com.sangwoo.possystem.ui.EmployeeLoginSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.io.File;

// TODO: 2017. 6. 11. Impl init table state
public class MainPresenter implements MainContract.Presenter {
    private static final Logger logger = LogManager.getLogger();

    private MainContract.View view;

    private final InquiryEmployee inquiryEmployeeUseCase;
    private final LoadData loadDataUseCase;
    private final GetTables getTablesUseCase;

    @Inject
    MainPresenter(InquiryEmployee inquiryEmployee, LoadData loadData, GetTables getTables) {
        inquiryEmployeeUseCase = inquiryEmployee;
        loadDataUseCase = loadData;
        getTablesUseCase = getTables;
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
        inquiryEmployeeUseCase.clear();

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
