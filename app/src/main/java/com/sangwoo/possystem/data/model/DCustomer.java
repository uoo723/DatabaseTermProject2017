package com.sangwoo.possystem.data.model;

import org.davidmoten.rx.jdbc.annotations.Column;

import java.math.BigDecimal;

public interface DCustomer {

    @Column("ID")
    BigDecimal id();

    @Column("NAME")
    String name();

    @Column("BIRTH_DATE")
    String birthDate();

    @Column("PHONE_NUM")
    String phoneNum();

    @Column("LEVEL")
    String level();

    @Column("PURCHASE_AMOUNT")
    BigDecimal purchaseAmount();
}
