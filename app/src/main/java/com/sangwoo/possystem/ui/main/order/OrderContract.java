package com.sangwoo.possystem.ui.main.order;

import com.sangwoo.possystem.domain.model.Menu;
import com.sangwoo.possystem.domain.model.Table;
import com.sangwoo.possystem.ui.BasePresenter;
import com.sangwoo.possystem.ui.BaseView;

import java.util.List;

public interface OrderContract {
    interface View extends BaseView {
        void succeedOrder(int tableNum);
        void failedOrder();
        void succeedCancel(int tableNum);
        void failedCancel();
        void succeedPay(int tableNum);
        void failedPay(String message);
        void showMenuList(List<Menu> menuList);
        void emptyMenu();
        void showTableState(List<Table> tables);
        void cancelOrder(int tableNum, String message);
        void failedAddMenu();
        void requiredEmployeeLogin();
    }

    interface Presenter extends BasePresenter<View> {
        void loadTablesInfo();
        void loadOrderInfo(int tableNum);
        void order();
        void addMenu(Menu menu);
        void cancel();
        void pay(String customerName);
    }
}
