package com.sangwoo.possystem.ui.main.admin.customer;

import com.sangwoo.possystem.domain.model.Customer;
import com.sangwoo.possystem.ui.BasePresenter;
import com.sangwoo.possystem.ui.BaseView;

interface CustomerTabContract {
    interface View extends BaseView {
        void requiredEmployeeLogin();
        void showCustomerInfo(Customer info);
        void failedInquiry(String message);
        void succeedRegistration();
        void failedRegistration(String message);
        void registerClickedResult(boolean permission);
    }

    interface Presenter extends BasePresenter<View> {
        void inquiryCustomer(String name);
        void registerCustomer(String name, String birth, String phoneNum, Customer.Level level);
        void registerClicked();
    }
}
