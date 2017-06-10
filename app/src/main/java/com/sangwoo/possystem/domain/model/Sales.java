package com.sangwoo.possystem.domain.model;

import java.util.Date;

public class Sales {
    private Date date;
    private Menu mostSoldMenu;
    private Menu leastSoldMenu;
    private int todaySales;
    private int accumulatedSales;

    public Sales(Date date, Menu mostSoldMenu, Menu leastSoldMenu, int todaySales, int accumulatedSales) {
        this.date = date;
        this.mostSoldMenu = mostSoldMenu;
        this.leastSoldMenu = leastSoldMenu;
        this.todaySales = todaySales;
        this.accumulatedSales = accumulatedSales;
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

    public int getTodaySales() {
        return todaySales;
    }

    public void setTodaySales(int todaySales) {
        this.todaySales = todaySales;
    }

    public int getAccumulatedSales() {
        return accumulatedSales;
    }

    public void setAccumulatedSales(int accumulatedSales) {
        this.accumulatedSales = accumulatedSales;
    }

    @Override
    public String toString() {
        return "date: " + date + "mostSoldMenu: " + mostSoldMenu + ", leastSoldMenu: " + leastSoldMenu +
                ", todaySales: " + todaySales + ", accumulatedSales: " + accumulatedSales;
    }
}
