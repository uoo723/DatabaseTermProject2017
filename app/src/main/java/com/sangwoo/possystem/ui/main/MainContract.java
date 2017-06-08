package com.sangwoo.possystem.ui.main;

import com.sangwoo.possystem.ui.BasePresenter;
import com.sangwoo.possystem.ui.BaseView;

import java.io.File;

interface MainContract {
    interface View extends BaseView {
        void succeedLoadingData();
        void failedLoadingData();
        void succeedEmployeeLogin();
        void failedEmployeeLogin();
    }

    interface Presenter extends BasePresenter<View> {
        void loadData(File file);
        void employeeLogin(String name, String employeeId);
    }
}
