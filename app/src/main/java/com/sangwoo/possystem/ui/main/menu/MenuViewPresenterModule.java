package com.sangwoo.possystem.ui.main.menu;

import com.sangwoo.possystem.common.scope.TabScope;
import dagger.Module;
import dagger.Provides;

@Module
class MenuViewPresenterModule {

    @Provides
    @TabScope
    MenuViewContract.Presenter providePresenter(MenuViewPresenter presenter) {
        return presenter;
    }
}
