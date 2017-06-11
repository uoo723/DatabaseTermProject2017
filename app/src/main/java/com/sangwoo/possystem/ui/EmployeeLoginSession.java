package com.sangwoo.possystem.ui;

import com.sangwoo.possystem.domain.model.Employee;

public final class EmployeeLoginSession {
    private static Employee employee;

    public static void login(Employee employee) {
        EmployeeLoginSession.employee = employee;
    }

    public static void logout() {
        employee = null;
    }

    public static Employee getEmployee() {
        return employee;
    }

    public static boolean isLogin() {
        return employee != null;
    }
}
