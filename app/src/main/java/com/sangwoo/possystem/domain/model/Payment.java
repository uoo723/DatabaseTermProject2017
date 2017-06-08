package com.sangwoo.possystem.domain.model;

import java.util.Date;

public final class Payment {
    private int id;
    private Date date;
    private Order order;
    private Customer payer;

    public Payment(int id, Date date, Order order, Customer payer) {
        this.id = id;
        this.date = date;
        this.order = order;
        this.payer = payer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Customer getPayer() {
        return payer;
    }

    public void setPayer(Customer payer) {
        this.payer = payer;
    }
}
