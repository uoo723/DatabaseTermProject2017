package com.sangwoo.possystem.data.model;

import org.davidmoten.rx.jdbc.annotations.Column;

import java.math.BigDecimal;

public interface DMenu {
    @Column("ID")
    BigDecimal id();

    @Column("NAME")
    String name();

    @Column("PRICE")
    BigDecimal price();
}
