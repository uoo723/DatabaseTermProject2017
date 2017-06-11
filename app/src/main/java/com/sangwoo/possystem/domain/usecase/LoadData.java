package com.sangwoo.possystem.domain.usecase;

import com.sangwoo.possystem.common.utils.DateUtils;
import com.sangwoo.possystem.common.utils.StringUtils;
import com.sangwoo.possystem.domain.executor.PostExecutionThread;
import com.sangwoo.possystem.domain.executor.ThreadExecutor;
import com.sangwoo.possystem.domain.model.Customer;
import com.sangwoo.possystem.domain.model.Employee;
import com.sangwoo.possystem.domain.model.Menu;
import com.sangwoo.possystem.domain.repository.DataSource;
import io.reactivex.Completable;
import io.reactivex.Observable;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class LoadData extends UseCase<File, Object> {

    private final DataSource dataSource;

    @Inject
    LoadData(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
             DataSource dataSource) {
        super(threadExecutor, postExecutionThread);
        this.dataSource = dataSource;
    }

    @Override
    Observable<Object> buildUseCaseObservable(File file) {
        return Completable.fromAction(() -> loadData(file)).toObservable();
    }

    private void loadData(File file) throws Exception {
        Scanner sc = new Scanner(new FileInputStream(file), "UTF-8");

        int count = sc.nextInt();

        for (int i = 0; i < count; i++) {
            String name = sc.next();
            Date birth = DateUtils.convertToBirthDate(sc.next());
            String phoneNum = sc.next();
            Customer.Level level = Customer.Level.parse(sc.next());

            int purchaseAmount = Customer.Level.getPurchaseAmount(level);

            Throwable t = dataSource.createCustomer(new Customer(-1, name, birth, phoneNum, level, purchaseAmount))
                    .blockingGet();

            if (t != null)
                throw new Exception(t);
        }

        count = sc.nextInt();

        for (int i = 0; i < count; i++) {
            String name = sc.next();
            Employee.Rank rank = Employee.Rank.parse(sc.next());

            Throwable t = dataSource.createEmployee(new Employee(-1, name,
                    StringUtils.generateFourNumString(), rank)).blockingGet();

            if (t != null)
                throw new Exception(t);
        }

        count = sc.nextInt();

        List<Menu> menus = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            String name = sc.next();
            int price = sc.nextInt();
            menus.add(new Menu(-1, name, price));
        }
        
        Throwable t = dataSource.createMenus(menus).blockingGet();

        if (t != null)
            throw new Exception(t);
    }
}
