package com.sangwoo.possystem.ui.main;

import com.sangwoo.possystem.domain.usecase.InquiryCustomer;
import com.sangwoo.possystem.domain.usecase.InquiryEmployee;
import com.sangwoo.possystem.domain.usecase.LoadData;
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
                Throwable::printStackTrace,
                () -> logger.info("success"));
    }

    @Override
    public void employeeLogin(String name, String employeeId) {
        // TODO: 2017. 6. 8. Impl employeeLogin()
        logger.info(String.format("name: %s, id: %s", name, employeeId));
        view.failedEmployeeLogin();
    }
}
