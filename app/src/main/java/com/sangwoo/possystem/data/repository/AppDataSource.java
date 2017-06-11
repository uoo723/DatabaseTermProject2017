package com.sangwoo.possystem.data.repository;

import com.sangwoo.possystem.common.utils.DateUtils;
import com.sangwoo.possystem.data.database.DatabaseProxyImpl;
import com.sangwoo.possystem.data.mapper.CustomerMapper;
import com.sangwoo.possystem.data.mapper.EmployeeMapper;
import com.sangwoo.possystem.data.mapper.MenuMapper;
import com.sangwoo.possystem.data.model.DCustomer;
import com.sangwoo.possystem.data.model.DEmployee;
import com.sangwoo.possystem.data.model.DMenu;
import com.sangwoo.possystem.data.model.DTable;
import com.sangwoo.possystem.domain.model.*;
import com.sangwoo.possystem.domain.repository.DataSource;
import io.reactivex.Completable;
import io.reactivex.Single;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

public class AppDataSource implements DataSource {
    private static final Logger logger = LogManager.getLogger();

    private final DatabaseProxyImpl databaseProxy;

    @Inject
    AppDataSource(DatabaseProxyImpl databaseProxy) {
        this.databaseProxy = databaseProxy;
    }

    @Override
    public Single<List<Table>> getTables() {
        return databaseProxy.getDatabase()
                .select("select * from \"TABLE\"")
                .autoMap(DTable.class)
                .map(dTable -> {
                    boolean ordering = databaseProxy.getDatabase()
                            .select("select * from \"ORDER\" where table_id=?")
                            .parameters(dTable.id().intValue())
                            .count()
                            .blockingGet() != 0;
                    return new Table(dTable.id().intValue(), dTable.tableNum().intValue(), ordering);
                })
                .toList();
    }

    @Override
    public Completable createOrder(Order order) {
        List<Completable> completables = new ArrayList<>(order.getMenus().size());

        for (Menu menu : order.getMenus()) {
            Completable completable = databaseProxy.getDatabase()
                    .update("insert into \"ORDER\"(table_id, menu_id) values (?, ?)")
                    .parameters(order.getTable().getId(), menu.getId())
                    .complete();
            completables.add(completable);
        }

        return Completable.merge(completables);
    }

    @Override
    public Single<Order> getOrder(int tableId) {
        Single<Table> tableSingle = databaseProxy.getDatabase()
                .select("select * from \"TABLE\" where id=?")
                .parameters(tableId)
                .autoMap(DTable.class)
                .map(dTable -> {
                    boolean ordering = databaseProxy.getDatabase()
                            .select("select * from \"ORDER\" where table_id=?")
                            .parameters(dTable.id().intValue())
                            .count()
                            .blockingGet() != 0;
                    return new Table(dTable.id().intValue(), dTable.tableNum().intValue(), ordering);
                })
                .singleOrError();

        Single<List<Menu>> menuListSingle = databaseProxy.getDatabase()
                .select("select menu_id id, name, price from \"ORDER\" o, \"TABLE\" t, menu m where o.table_id = t.id " +
                        "and o.menu_id = m.id and table_id = ?")
                .parameters(tableId)
                .autoMap(DMenu.class)
                .map(MenuMapper::toMenu)
                .toList();

        return Single.zip(tableSingle, menuListSingle, Order::new);
    }

    @Override
    public Completable updateOrder(Order order) {
        return Completable.mergeArray(deleteOrder(order.getTable().getId()), createOrder(order));
    }

    @Override
    public Completable deleteOrder(int tableId) {
        return databaseProxy.getDatabase()
                .update("delete from \"ORDER\" where table_id=?")
                .parameters(tableId)
                .complete();
    }

    @Override
    public Completable createMenus(List<Menu> menus) {
        List<Completable> completables = new ArrayList<>(menus.size());

        for (Menu menu : menus) {
            Completable completable = databaseProxy.getDatabase()
                    .update("insert into menu(name, price) values(?, ?)")
                    .parameters(menu.getName(), menu.getPrice())
                    .complete();
            completables.add(completable);
        }

        return Completable.merge(completables);
    }

    @Override
    public Completable createMenu(Menu menu) {
        return databaseProxy.getDatabase()
                .update("insert into menu(name, price) values(?, ?)")
                .parameters(menu.getName(), menu.getPrice())
                .complete();
    }

    @Override
    public Single<Menu> getMenu(String menuName) {
        return databaseProxy.getDatabase()
                .select("select * from menu where name=?")
                .parameters(menuName)
                .autoMap(DMenu.class)
                .map(MenuMapper::toMenu)
                .singleOrError();
    }

    @Override
    public Single<List<Menu>> getMenus() {
        return databaseProxy.getDatabase()
                .select("select * from menu")
                .autoMap(DMenu.class)
                .map(MenuMapper::toMenu)
                .toList();
    }

    @Override
    public Completable createPayment(Payment payment) {
        List<Completable> completables = new ArrayList<>(payment.getOrder().getMenus().size());
        List<Menu> menus = payment.getOrder().getMenus();

        String sql;

        if (payment.getPayer() != null) {
            sql = "insert into payment(menu_id, table_id, payer_id, employee_id, pay) values(?, ?, ?, ?, ?)";
        } else {
            sql = "insert into payment(menu_id, table_id, employee_id, pay) values(?, ?, ?, ?)";
        }

        for (int i = 0; i < menus.size(); i++) {
            Completable completable;

            if (payment.getPayer() != null) {
                completable = databaseProxy.getDatabase()
                        .update(sql)
                        .parameters(menus.get(i).getId(), payment.getOrder().getTable().getId(),
                                payment.getPayer().getId(),
                                payment.getEmployee().getId(), payment.getPays().get(i))
                        .complete();
            } else {
                completable = databaseProxy.getDatabase()
                        .update(sql)
                        .parameters(menus.get(i).getId(), payment.getOrder().getTable().getId(),
                                payment.getEmployee().getId(), payment.getPays().get(i))
                        .complete();
            }

            completables.add(completable);
        }

        return Completable.merge(completables);
    }

    @Override
    public Completable createCustomer(Customer customer) {
        String name = customer.getName();
        String birthDate = DateUtils.convertToBirthString(customer.getBirthDate());
        String phoneNum = customer.getPhoneNum();
        String level = customer.getLevel().getValue();
        int purchaseAmount = customer.getPurchaseAmount();

        return databaseProxy.getDatabase()
                .update("insert into customer(name, birth_date, phone_num, \"LEVEL\", purchase_amount) values " +
                        "(?, ?, ?, ?, ?)")
                .parameters(name, birthDate, phoneNum, level, purchaseAmount)
                .complete();
    }

    @Override
    public Single<Customer> getCustomer(String name) {
        return databaseProxy.getDatabase()
                .select("select * from customer where name=?")
                .parameters(name)
                .autoMap(DCustomer.class)
                .map(CustomerMapper::toCustomer)
                .singleOrError();
    }

    @Override
    public Completable updateCustomer(Customer customer) {
        int id = customer.getId();
        String name = customer.getName();
        String birthDate = DateUtils.convertToBirthString(customer.getBirthDate());
        String phoneNum = customer.getPhoneNum();
        String level = customer.getLevel().getValue();
        int purchaseAmount = customer.getPurchaseAmount();

        return databaseProxy.getDatabase()
                .update("update customer set name=?, birth_date=?, phone_num=?, \"LEVEL\"=?, purchase_amount=? " +
                        "where id=?")
                .parameters(name, birthDate, phoneNum, level, purchaseAmount, id)
                .complete();
    }

    @Override
    public Completable createEmployee(Employee employee) {
        String name = employee.getName();
        String employeeId = employee.getEmployeeId();
        String rank = employee.getRank().getValue();
        return databaseProxy.getDatabase()
                .update("insert into employee(name, employee_id, rank) values (?, ?, ?)")
                .parameters(name, employeeId, rank)
                .complete();
    }

    @Override
    public Single<Employee> getEmployee(String name) {
        return databaseProxy.getDatabase()
                .select("select * from employee where name=?")
                .parameters(name)
                .autoMap(DEmployee.class)
                .map(EmployeeMapper::toEmployee)
                .singleOrError();
    }

    @Override
    public Single<Sales> getSales(Date date) {
        return Single.fromCallable(() -> {
            List<Menu> menus = databaseProxy.getDatabase()
                    .select("select m.id, name, price, count(menu_id) c_menu from PAYMENT p right join menu m on " +
                            "p.menu_id = m.id group by m.id, m.name, price order by c_menu")
                    .getAs(BigDecimal.class, String.class, BigDecimal.class, BigDecimal.class)
                    .map(tuple -> new Menu(tuple._1().intValue(), tuple._2(), tuple._3().intValue()))
                    .toList()
                    .blockingGet();

            String begin = DateUtils.convertToString(DateUtils.convertBeginOfDay(date));
            String end = DateUtils.convertToString(DateUtils.convertEndOfDay(date));

            BigDecimal todaySales;

            try {
                todaySales = databaseProxy.getDatabase()
                        .select("select sum(pay) sum from PAYMENT where \"DATE\" BETWEEN to_date(?, " +
                                "'YY-MM-DD HH24:MI:SS') and to_date(?, 'YY-MM-DD HH24:MI:SS')")
                        .parameters(begin, end)
                        .getAs(BigDecimal.class)
                        .blockingFirst();
            } catch (NullPointerException e) {
                todaySales = new BigDecimal(0);
            }

            BigDecimal accumulatedSales;

            try {
                accumulatedSales = databaseProxy.getDatabase()
                        .select("select sum(pay) from PAYMENT where \"DATE\" <= to_date(?, " +
                                "'YY-MM-DD HH24:MI:SS')")
                        .parameters(end)
                        .getAs(BigDecimal.class)
                        .blockingFirst();
            } catch (NullPointerException e) {
                accumulatedSales = new BigDecimal(0);
            }

            return new Sales(date, menus.get(menus.size() - 1), menus.get(0), todaySales.intValue(),
                        accumulatedSales.intValue());
        });
    }
}
