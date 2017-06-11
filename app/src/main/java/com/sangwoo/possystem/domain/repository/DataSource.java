package com.sangwoo.possystem.domain.repository;

import com.sangwoo.possystem.domain.model.*;
import io.reactivex.Completable;
import io.reactivex.Single;

import java.util.Date;
import java.util.List;

public interface DataSource {
    Single<List<Table>> getTables();

    Completable createOrder(Order order);
    Single<Order> getOrder(int tableId);
    Completable updateOrder(Order order);
    Completable deleteOrder(int tableId);

    Completable createMenus(List<Menu> menus);
    Completable createMenu(Menu menu);
    Single<Menu> getMenu(String menuName);
    Single<List<Menu>> getMenus();

    Completable createPayment(Payment payment);

    Completable createCustomer(Customer customer);
    Single<Customer> getCustomer(String name);
    Completable updateCustomer(Customer customer);

    Completable createEmployee(Employee employee);
    Single<Employee> getEmployee(String name);

    Single<Sales> getSales(Date date);
}