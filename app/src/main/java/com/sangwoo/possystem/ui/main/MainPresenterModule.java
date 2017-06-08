package com.sangwoo.possystem.ui.main;

import com.sangwoo.possystem.common.scope.ViewScope;
import dagger.Module;
import dagger.Provides;

@Module
class MainPresenterModule {

    @Provides
    @ViewScope
    MainContract.Presenter providePresenter(MainPresenter presenter) {
        return presenter;
    }
}
