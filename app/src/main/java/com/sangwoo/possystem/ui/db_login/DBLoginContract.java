package com.sangwoo.possystem.ui.db_login;

import com.sangwoo.possystem.ui.BasePresenter;
import com.sangwoo.possystem.ui.BaseView;

interface DBLoginContract {
    interface View extends BaseView {
        void loginResult(boolean succeed);
    }

    interface Presenter extends BasePresenter<View> {
        void login(String host, String user, String password);
    }
}
