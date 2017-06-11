package com.sangwoo.possystem.domain.model;

import java.util.ArrayList;
import java.util.List;

public final class Payment {
    private Order order;
    private Customer payer;
    private Employee employee;
    private List<Integer> pays;

    public Payment(Order order, Customer payer, Employee employee) {
        this.order = order;
        this.payer = payer;
        this.employee = employee;
        this.pays = calculatePay();
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Integer> getPays() {
        return pays;
    }

    public void setPays(List<Integer> pays) {
        this.pays = pays;
    }

    private List<Integer> calculatePay() {
        List<Integer> pays = new ArrayList<>(order.getMenus().size());

        float discount = 0f;

        if (payer != null) {
            switch (payer.getLevel()) {
            case GOLD:
                discount = .3f;
                break;
            case SILVER:
                discount = .2f;
                break;
            case BRONZE:
                discount = .1f;
                break;
            }
        }

        for (Menu menu : order.getMenus()) {
            pays.add((int) (menu.getPrice() * (1 - discount)));
        }

        return pays;
    }
}
