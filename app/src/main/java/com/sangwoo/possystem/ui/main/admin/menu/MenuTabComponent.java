package com.sangwoo.possystem.ui.main.admin.menu;

import com.sangwoo.possystem.AppComponent;
import com.sangwoo.possystem.common.scope.TabScope;
import dagger.Component;

@TabScope
@Component(dependencies = AppComponent.class, modules = MenuTabPresenterModule.class)
interface MenuTabComponent {
    void inject(MenuTab target);
}
