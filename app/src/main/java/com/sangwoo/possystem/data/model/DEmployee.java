package com.sangwoo.possystem.data.model;

import org.davidmoten.rx.jdbc.annotations.Column;

import java.math.BigDecimal;

public interface DEmployee {

    @Column("ID")
    BigDecimal id();

    @Column("NAME")
    String name();

    @Column("EMPLOYEE_ID")
    String employeeId();

    @Column("RANK")
    String rank();
}
