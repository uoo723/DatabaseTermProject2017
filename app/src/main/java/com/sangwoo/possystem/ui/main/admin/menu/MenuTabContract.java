package com.sangwoo.possystem.ui.main.admin.menu;

import com.sangwoo.possystem.domain.model.Menu;
import com.sangwoo.possystem.ui.BasePresenter;
import com.sangwoo.possystem.ui.BaseView;

interface MenuTabContract {
    interface View extends BaseView {
        void requiredEmployeeLoin();
        void succeedRegistration();
        void failedRegistration(String message);
        void showMenuInfo(Menu menu);
        void failedInquiry(String message);
        void registerClickedResult(boolean permission);
    }

    interface Presenter extends BasePresenter<View> {
        void inquiry(String name);
        void registerClicked();
        void register(String menuName, String price);
    }
}
