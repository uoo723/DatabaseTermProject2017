package com.sangwoo.possystem.ui.main.admin.sales;

import com.sangwoo.possystem.domain.model.Sales;
import com.sangwoo.possystem.ui.BasePresenter;
import com.sangwoo.possystem.ui.BaseView;

import java.util.Date;

public interface SalesTabContract {
    interface View extends BaseView {
        void requiredEmployeeLogin();
        void failedInquiry(String message);
        void showSales(Sales sales);
    }

    interface Presenter extends BasePresenter<View> {
        void inquiry(Date date);
    }
}
