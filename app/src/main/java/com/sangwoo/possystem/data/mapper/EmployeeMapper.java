package com.sangwoo.possystem.data.mapper;

import com.sangwoo.possystem.data.model.DEmployee;
import com.sangwoo.possystem.domain.model.Employee;

public class EmployeeMapper {

    public static Employee toEmployee(DEmployee dEmployee) {
        int id = dEmployee.id().intValue();
        String name = dEmployee.name();
        String employeeId = dEmployee.employeeId();
        Employee.Rank rank = Employee.Rank.parse(dEmployee.rank());

        return new Employee(id, name, employeeId, rank);
    }
}
