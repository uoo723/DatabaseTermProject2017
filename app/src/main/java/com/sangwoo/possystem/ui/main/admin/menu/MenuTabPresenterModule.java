package com.sangwoo.possystem.ui.main.admin.menu;

import com.sangwoo.possystem.common.scope.TabScope;
import dagger.Module;
import dagger.Provides;

@Module
class MenuTabPresenterModule {

    @Provides
    @TabScope
    MenuTabContract.Presenter providePresenter(MenuTabPresenter presenter) {
        return presenter;
    }
}
