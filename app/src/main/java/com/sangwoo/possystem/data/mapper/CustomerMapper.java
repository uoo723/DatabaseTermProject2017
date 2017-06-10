package com.sangwoo.possystem.data.mapper;

import com.sangwoo.possystem.common.utils.DateUtils;
import com.sangwoo.possystem.data.model.DCustomer;
import com.sangwoo.possystem.domain.model.Customer;

import java.util.Calendar;
import java.util.Date;

public final class CustomerMapper {

    public static Customer toCustomer(DCustomer dCustomer) throws Exception {
        int id = dCustomer.id().intValue();
        String name = dCustomer.name();
        Date birthDate = DateUtils.convertToBirthDate(dCustomer.birthDate());
        String phoneNum = dCustomer.phoneNum();
        Customer.Level level = Customer.Level.parse(dCustomer.level());
        int purchaseAmount =  dCustomer.purchaseAmount().intValue();

        return new Customer(id, name, birthDate, phoneNum, level, purchaseAmount);
    }
}
