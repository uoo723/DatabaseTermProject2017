package com.sangwoo.possystem.ui.main.order;

import com.sangwoo.possystem.AppComponent;
import com.sangwoo.possystem.common.scope.ViewSectionScope;
import dagger.Component;

@ViewSectionScope
@Component(dependencies = AppComponent.class, modules = OrderPresenterModule.class)
interface OrderComponent {
    void inject(OrderView target);
}
