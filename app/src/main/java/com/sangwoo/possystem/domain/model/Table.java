package com.sangwoo.possystem.domain.model;

public final class Table {
    private int id;
    private int tableNum;
    private boolean paymentCompleted;

    public Table(int id, int tableNum, boolean paymentCompleted) {
        this.id = id;
        this.tableNum = tableNum;
        this.paymentCompleted = paymentCompleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTableNum() {
        return tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }

    public boolean isPaymentCompleted() {
        return paymentCompleted;
    }

    public void setPaymentCompleted(boolean paymentCompleted) {
        this.paymentCompleted = paymentCompleted;
    }
}
