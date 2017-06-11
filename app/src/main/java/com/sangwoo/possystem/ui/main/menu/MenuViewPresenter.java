package com.sangwoo.possystem.ui.main.menu;

import com.sangwoo.possystem.domain.usecase.GetMenuList;

import javax.inject.Inject;

public class MenuViewPresenter implements MenuViewContract.Presenter {

    private MenuViewContract.View view;

    private final GetMenuList getMenuListUseCase;

    @Inject
    MenuViewPresenter(GetMenuList getMenuList) {
        getMenuListUseCase = getMenuList;
    }

    @Override
    public void bindView(MenuViewContract.View view) {
        this.view = view;
    }

    @Override
    public void loadMenu() {
        getMenuListUseCase.execute(null, view::succeedLoad,
                throwable -> view.failedLoad());
    }
}
