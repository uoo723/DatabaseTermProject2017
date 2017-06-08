package com.sangwoo.possystem.domain.model;

import java.util.Date;

public class Sales {
    private int id;
    private Date date;
    private Menu mostSoldMenu;
    private Menu leastSoldMenu;
    private int accumulatedSales;

    public Sales(int id, Date date, Menu mostSoldMenu, Menu leastSoldMenu, int accumulatedSales) {
        this.id = id;
        this.date = date;
        this.mostSoldMenu = mostSoldMenu;
        this.leastSoldMenu = leastSoldMenu;
        this.accumulatedSales = accumulatedSales;
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

    public Menu getMostSoldMenu() {
        return mostSoldMenu;
    }

    public void setMostSoldMenu(Menu mostSoldMenu) {
        this.mostSoldMenu = mostSoldMenu;
    }

    public Menu getLeastSoldMenu() {
        return leastSoldMenu;
    }

    public void setLeastSoldMenu(Menu leastSoldMenu) {
        this.leastSoldMenu = leastSoldMenu;
    }

    public int getAccumulatedSales() {
        return accumulatedSales;
    }

    public void setAccumulatedSales(int accumulatedSales) {
        this.accumulatedSales = accumulatedSales;
    }
}
