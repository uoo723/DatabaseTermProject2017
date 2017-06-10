package com.sangwoo.possystem.data.mapper;

import com.sangwoo.possystem.data.model.DEmployee;
import com.sangwoo.possystem.domain.model.Employee;

public class EmployeeMapper {

    public static Employee toEmployee(DEmployee dEmployee) {
        int id = dEmployee.id().intValue();
        String name = dEmployee.name();
        String employeeId = dEmployee.employeeId();
        Employee.Rank rank = parseRank(dEmployee.rank());

        return new Employee(id, name, employeeId, rank);
    }

    private static Employee.Rank parseRank(String rankStr) {
        Employee.Rank rank;

        if (Employee.Rank.SUPERVISOR.getValue().equals(rankStr)) {
            rank = Employee.Rank.SUPERVISOR;
        } else {
            rank = Employee.Rank.STAFF;
        }

        return rank;
    }
}
