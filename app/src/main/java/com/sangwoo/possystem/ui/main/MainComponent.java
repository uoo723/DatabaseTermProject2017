package com.sangwoo.possystem.ui.main;

import com.sangwoo.possystem.AppComponent;
import com.sangwoo.possystem.common.scope.ViewScope;
import dagger.Component;

@ViewScope
@Component(dependencies = AppComponent.class, modules = MainPresenterModule.class)
interface MainComponent {
    void inject(MainView target);
}
