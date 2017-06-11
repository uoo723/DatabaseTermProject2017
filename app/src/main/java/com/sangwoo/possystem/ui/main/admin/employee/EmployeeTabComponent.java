package com.sangwoo.possystem.ui.main.admin.employee;

import com.sangwoo.possystem.AppComponent;
import com.sangwoo.possystem.common.scope.TabScope;
import dagger.Component;

@TabScope
@Component(dependencies = AppComponent.class, modules = EmployeeTabPresenterModule.class)
interface EmployeeTabComponent {
    void inject(EmployeeTab target);
}
