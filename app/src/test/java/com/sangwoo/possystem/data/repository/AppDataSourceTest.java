package com.sangwoo.possystem.data.repository;

import com.sangwoo.possystem.data.database.DatabaseProxyImpl;
import com.sangwoo.possystem.domain.database.DatabaseProxy;
import com.sangwoo.possystem.domain.model.*;
import com.sangwoo.possystem.domain.repository.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

public class AppDataSourceTest {

    private static final Logger logger = LogManager.getLogger();

    private DatabaseProxyImpl databaseProxy;
    private DataSource dataSource;

    @Before
    public void init() throws Exception {
        logger.debug("init");
        databaseProxy = new DatabaseProxyImpl();
        dataSource = new AppDataSource(databaseProxy);
        assertNull(databaseProxy
                .login("192.168.56.101", "possystem", "possystem")
                .blockingGet());
    }

    @After
    public void after() throws Exception {
        logger.debug("after");
        databaseProxy.getDatabase().close();
    }

//    @Test
    public void getTables() throws Exception {
        List<Table> tables = dataSource.getTables().blockingGet();
        tables.forEach(logger::info);
        assertEquals(tables.size(), 20);
    }

//    @Test
    public void createOrder() throws Exception {
        Table table = new Table(1, 1, false);
        List<Menu> menu = new ArrayList<>();
        menu.add(new Menu(23, "Susi", 180000));
        menu.add(new Menu(24, "Gimchi", 2000));

        Order order = new Order(table, menu);

        assertNull(dataSource.createOrder(order).blockingGet());
    }

//    @Test
    public void getMenu() throws Exception {
        String menuName = "크림리조또";
        Menu menu = dataSource.getMenu(menuName).blockingGet();
        assertEquals(menuName, menu.getName());
    }

//    @Test
    public void getOrder() throws Exception {
        Order order = dataSource.getOrder(1).blockingGet();
        assertEquals(order.getTable().getId(), 1);
    }

//    @Test
    public void updateOrder() throws Exception {
        Table table = new Table(1, 1, false);
        List<Menu> menus = new ArrayList<>();
        menus.add(new Menu(23, "Susi", 180000));
        menus.add(new Menu(24, "Gimchi", 2000));
        Order order = new Order(table, menus);
        assertNull(dataSource.updateOrder(order).blockingGet());
    }

//    @Test
    public void deleteOrder() throws Exception {
        assertNull(dataSource.deleteOrder(1).blockingGet());
    }

//    @Test
    public void createMenus() throws Exception {
        List<Menu> menus = new ArrayList<>();
        menus.add(new Menu(-1, "Spaghetti", 340000));
        menus.add(new Menu(-1, "크림리조또", 13000));
        assertNull(dataSource.createMenus(menus).blockingGet());
    }

//    @Test
    public void createMenu() throws Exception {
        Menu menu = new Menu(-1, "까르보나라", 12000);
        assertNull(dataSource.createMenu(menu).blockingGet());
    }

//    @Test
    public void getMenus() throws Exception {
        List<Menu> menus = dataSource.getMenus().blockingGet();
        menus.forEach(logger::info);
        assertNotNull(menus);
    }

//    @Test
    public void createPayment() throws Exception {
        Order order = dataSource.getOrder(1).blockingGet();
        Table table = dataSource.getTables().blockingGet().get(0);
        Customer customer = dataSource.getCustomer("Han").blockingGet();
        Employee employee = dataSource.getEmployee("Sang").blockingGet();
        Payment payment = new Payment(order, customer, employee);
        assertNull(dataSource.createPayment(payment).blockingGet());
    }

//    @Test
    public void createCustomer() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(1996, 8, 6);
        Customer customer = new Customer(-1, "철수", cal.getTime(), "1000", Customer.Level.GOLD, 12000);
        assertNull(dataSource.createCustomer(customer).blockingGet());
    }

//    @Test
    public void getCustomer() throws Exception {
        Customer customer = dataSource.getCustomer("홍길동").blockingGet();
        assertEquals("홍길동", customer.getName());
    }

//    @Test
    public void getSales() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(2017, 5, 10);
        try {
            Sales sales = dataSource.getSales(cal.getTime()).blockingGet();
            assertNotNull(sales);
            logger.info(sales);
        } catch (NullPointerException ignored) {
            assertNull(null);
        }
    }

//    @Test
    public void createEmployee() throws Exception {
        Employee employee = new Employee(-1, "Song", "1333", Employee.Rank.STAFF);
        assertNull(dataSource.createEmployee(employee).blockingGet());
    }

//    @Test
    public void getEmployee() throws Exception {
        Employee employee = dataSource.getEmployee("Song").blockingGet();
        assertEquals("Song", employee.getName());
    }


}