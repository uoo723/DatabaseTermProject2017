package com.sangwoo.possystem.ui.main.admin.customer;

import com.sangwoo.possystem.AppComponent;
import com.sangwoo.possystem.common.scope.TabScope;
import dagger.Component;

@TabScope
@Component(dependencies = AppComponent.class, modules = CustomerTabPresenterModule.class)
interface CustomerTabComponent {
    void inject(CustomerTab target);
}
