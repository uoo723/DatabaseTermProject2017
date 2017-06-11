package com.sangwoo.possystem.ui.main.admin.employee;

import com.sangwoo.possystem.common.scope.TabScope;
import dagger.Module;
import dagger.Provides;

@Module
class EmployeeTabPresenterModule {

    @Provides
    @TabScope
    EmployeeTabContract.Presenter providePresenter(EmployeeTabPresenter presenter) {
        return presenter;
    }
}
