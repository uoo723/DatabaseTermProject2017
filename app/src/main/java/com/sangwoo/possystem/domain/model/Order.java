package com.sangwoo.possystem.domain.model;

import java.util.List;

public final class Order {
    private int id;
    private Table table;
    private List<Menu> menus;

    public Order(int id, Table table, List<Menu> menus) {
        this.id = id;
        this.table = table;
        this.menus = menus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}
