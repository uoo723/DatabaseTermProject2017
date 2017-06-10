package com.sangwoo.possystem.ui.main;

import com.sangwoo.possystem.domain.usecase.InquiryCustomer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.io.File;

public class MainPresenter implements MainContract.Presenter {
    private static final Logger logger = LogManager.getLogger();

    private MainContract.View view;

    @Inject
    MainPresenter(InquiryCustomer customer) {}

    @Override
    public void bindView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void loadData(File file) {
    }

    @Override
    public void employeeLogin(String name, String employeeId) {
        // TODO: 2017. 6. 8. Impl employeeLogin()
        logger.info(String.format("name: %s, id: %s", name, employeeId));
        view.failedEmployeeLogin();
    }
}
