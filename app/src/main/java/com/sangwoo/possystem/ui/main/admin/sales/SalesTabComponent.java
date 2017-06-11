package com.sangwoo.possystem.ui.main.admin.sales;

import com.sangwoo.possystem.AppComponent;
import com.sangwoo.possystem.common.scope.TabScope;
import dagger.Component;

@TabScope
@Component(dependencies = AppComponent.class, modules = SalesTabPresenterModule.class)
interface SalesTabComponent {
    void inject(SalesTab target);
}
