package com.sangwoo.possystem.ui.main.order;

import com.sangwoo.possystem.common.scope.ViewSectionScope;
import dagger.Module;
import dagger.Provides;

@Module
class OrderPresenterModule {

    @Provides
    @ViewSectionScope
    OrderContract.Presenter providePresenter(OrderPresenter presenter) {
        return presenter;
    }
}
