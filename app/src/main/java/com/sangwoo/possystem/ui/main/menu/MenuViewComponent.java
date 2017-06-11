package com.sangwoo.possystem.ui.main.menu;

import com.sangwoo.possystem.AppComponent;
import com.sangwoo.possystem.common.scope.TabScope;
import dagger.Component;

@TabScope
@Component(dependencies = AppComponent.class, modules = MenuViewPresenterModule.class)
interface MenuViewComponent {
    void inject(MenuView target);
}
