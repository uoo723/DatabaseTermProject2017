package com.sangwoo.possystem.data.mapper;

import com.sangwoo.possystem.data.model.DCustomer;
import com.sangwoo.possystem.domain.model.Customer;

import java.util.Calendar;
import java.util.Date;

public final class CustomerMapper {

    public static Customer toCustomer(DCustomer dCustomer) {
        int id = dCustomer.id().intValue();
        String name = dCustomer.name();
        Date birthDate = parseDate(dCustomer.birthDate());
        String phoneNum = dCustomer.phoneNum();
        Customer.Level level = parseLevel(dCustomer.level());
        int purchaseAmount =  dCustomer.purchaseAmount().intValue();

        return new Customer(id, name, birthDate, phoneNum, level, purchaseAmount);
    }

    private static Date parseDate(String date) {
        String month = date.charAt(0) + date.charAt(1) + "";
        String day = date.charAt(2) + date.charAt(3) + "";

        Calendar cal = Calendar.getInstance();
        cal.set(0, Integer.parseInt(month), Integer.parseInt(day));

        return cal.getTime();
    }

    private static Customer.Level parseLevel(String levelStr) {
        Customer.Level level;

        if (Customer.Level.GOLD.getValue().equals(levelStr)) {
            level = Customer.Level.GOLD;
        } else if (Customer.Level.SILVER.getValue().equals(levelStr)) {
            level = Customer.Level.SILVER;
        } else if (Customer.Level.BRONZE.getValue().equals(levelStr)) {
            level = Customer.Level.BRONZE;
        } else {
            level = Customer.Level.NORMAL;
        }

        return level;
    }
}
