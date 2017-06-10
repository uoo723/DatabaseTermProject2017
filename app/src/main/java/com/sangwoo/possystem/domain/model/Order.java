package com.sangwoo.possystem.domain.model;

import java.util.List;

public final class Order {
    private Table table;
    private List<Menu> menus;

    public Order(Table table, List<Menu> menus) {
        this.table = table;
        this.menus = menus;
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
