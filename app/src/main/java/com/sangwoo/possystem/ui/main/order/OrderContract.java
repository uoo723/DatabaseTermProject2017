package com.sangwoo.possystem.ui.main.order;

import com.sangwoo.possystem.domain.model.Menu;
import com.sangwoo.possystem.ui.BasePresenter;
import com.sangwoo.possystem.ui.BaseView;

public interface OrderContract {
    interface View extends BaseView {
        void succeedOrder();
        void failedOrder();
        void succeedCancel();
        void failedCancel();
        void succeedPay();
        void failedPay(String message);
        void showMenuList(String menuList);
    }

    interface Presenter extends BasePresenter<View> {
        void order(String customer, int tableNum);
        void addMenu(Menu menu);
        void cancel();
        void pay();
    }
}
