package com.sangwoo.possystem.ui.main.menu;

import com.sangwoo.possystem.domain.model.Menu;
import com.sangwoo.possystem.ui.BasePresenter;
import com.sangwoo.possystem.ui.BaseView;

import java.util.List;

interface MenuViewContract {
    interface View extends BaseView {
        void succeedLoad(List<Menu> menus);
        void failedLoad();
    }

    interface Presenter extends BasePresenter<View> {
        void loadMenu();
    }
}
