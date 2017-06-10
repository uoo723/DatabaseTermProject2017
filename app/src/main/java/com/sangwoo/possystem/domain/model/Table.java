package com.sangwoo.possystem.domain.model;

public final class Table {
    private int id;
    private int tableNum;
    private boolean ordering;

    public Table(int id, int tableNum, boolean paymentCompleted) {
        this.id = id;
        this.tableNum = tableNum;
        this.ordering = paymentCompleted;
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

    public boolean isOrdering() {
        return ordering;
    }

    public void setOrdering(boolean ordering) {
        this.ordering = ordering;
    }

    @Override
    public String toString() {
        return "id: " + id + ", tableNum: " + tableNum + ", ordering: " + ordering;
    }
}
