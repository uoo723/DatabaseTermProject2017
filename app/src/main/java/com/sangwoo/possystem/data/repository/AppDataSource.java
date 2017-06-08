package com.sangwoo.possystem.data.repository;

import com.sangwoo.possystem.data.database.DatabaseProxyImpl;
import com.sangwoo.possystem.domain.model.*;
import com.sangwoo.possystem.domain.repository.DataSource;
import io.reactivex.Completable;
import io.reactivex.Single;
import org.davidmoten.rx.jdbc.Database;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

// TODO - Impl AppDataSource
public class AppDataSource implements DataSource {
    private final Database database;

    @Inject
    AppDataSource(DatabaseProxyImpl databaseProxy) {
        database = databaseProxy.getDatabase();
    }

    @Override
    public Completable createTables(List<Table> tables) {
        return null;
    }

    @Override
    public Single<List<Table>> getTables() {
        return null;
    }

    @Override
    public Completable updateTable(Table table) {
        return null;
    }

    @Override
    public Completable createOrder(Order order) {
        return null;
    }

    @Override
    public Single<Order> getOrder(int tableId) {
        return null;
    }

    @Override
    public Completable updateOrder(Order order) {
        return null;
    }

    @Override
    public Completable deleteOrder(int tableId) {
        return null;
    }

    @Override
    public Completable createMenus(List<Menu> menus) {
        return null;
    }

    @Override
    public Completable createMenu(Menu menu) {
        return null;
    }

    @Override
    public Single<Menu> getMenu(String menuName) {
        return null;
    }

    @Override
    public Completable createPayment(Payment payment) {
        return null;
    }

    @Override
    public Completable createCustomer(Customer customer) {
        return null;
    }

    @Override
    public Single<Customer> getCustomer(String name) {
        return null;
    }

    @Override
    public Completable createEmployee(Employee employee) {
        return null;
    }

    @Override
    public Single<Employee> getEmployee(String name) {
        return null;
    }

    @Override
    public Single<Sales> getSales(Date date) {
        return null;
    }
}
