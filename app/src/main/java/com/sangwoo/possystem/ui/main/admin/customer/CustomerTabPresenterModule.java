package com.sangwoo.possystem.ui.main.admin.customer;

import com.sangwoo.possystem.common.scope.TabScope;
import dagger.Module;
import dagger.Provides;

@Module
class CustomerTabPresenterModule {

    @Provides
    @TabScope
    CustomerTabContract.Presenter providePresenter(CustomerTabPresenter presenter) {
        return presenter;
    }
}
