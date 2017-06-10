package com.sangwoo.possystem.data.model;

import org.davidmoten.rx.jdbc.annotations.Column;

import java.math.BigDecimal;

public interface DTable {

    @Column("ID")
    BigDecimal id();

    @Column("TABLE_NUM")
    BigDecimal tableNum();
}
