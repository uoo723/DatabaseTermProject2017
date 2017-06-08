package com.sangwoo.possystem.ui.db_login;

import com.sangwoo.possystem.AppComponent;
import com.sangwoo.possystem.common.scope.ViewScope;
import dagger.Component;

@ViewScope
@Component(dependencies = AppComponent.class, modules = DBLoginPresenterModule.class)
interface DBLoginComponent {
    void inject(DBLoginView target);
}
