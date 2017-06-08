package com.sangwoo.possystem.ui.db_login;

import com.sangwoo.possystem.common.scope.ViewScope;
import dagger.Module;
import dagger.Provides;

@Module
class DBLoginPresenterModule {

    @Provides
    @ViewScope
    DBLoginContract.Presenter providePresenter(DBLoginPresenter presenter) {
        return presenter;
    }
}
