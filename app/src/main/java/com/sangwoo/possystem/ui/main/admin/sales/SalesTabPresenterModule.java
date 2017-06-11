package com.sangwoo.possystem.ui.main.admin.sales;

import com.sangwoo.possystem.common.scope.TabScope;
import dagger.Module;
import dagger.Provides;

@Module
class SalesTabPresenterModule {

    @Provides
    @TabScope
    SalesTabContract.Presenter providePresenter(SalesTabPresenter presenter) {
        return presenter;
    }
}
