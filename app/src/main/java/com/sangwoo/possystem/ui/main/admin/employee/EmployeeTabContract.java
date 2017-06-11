package com.sangwoo.possystem.ui.main.admin.employee;

import com.sangwoo.possystem.domain.model.Employee;
import com.sangwoo.possystem.ui.BasePresenter;
import com.sangwoo.possystem.ui.BaseView;

interface EmployeeTabContract {
    interface View extends BaseView {
        void requiredEmployeeLogin();
        void showEmployeeInfo(Employee employee);
        void failedInquiry(String message);
        void registerClickedResult(boolean permission);
        void succeedRegistration(String message);
        void failedRegistration(String message);
    }

    interface Presenter extends BasePresenter<View> {
        void inquiry(String name);
        void registerClicked();
        void register(String name, Employee.Rank rank);
    }
}
