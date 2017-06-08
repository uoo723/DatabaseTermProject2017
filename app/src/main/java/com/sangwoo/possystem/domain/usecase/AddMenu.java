package com.sangwoo.possystem.domain.usecase;

import com.sangwoo.possystem.domain.executor.PostExecutionThread;
import com.sangwoo.possystem.domain.executor.ThreadExecutor;
import com.sangwoo.possystem.domain.model.Menu;
import com.sangwoo.possystem.domain.model.Order;
import com.sangwoo.possystem.domain.repository.DataSource;
import io.reactivex.Observable;

import javax.inject.Inject;

public class AddMenu extends UseCase<AddMenu.Params, Object> {
    private final DataSource dataSource;

    @Inject
    AddMenu(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
            DataSource dataSource) {
        super(threadExecutor, postExecutionThread);
        this.dataSource = dataSource;
    }

    @Override
    Observable<Object> buildUseCaseObservable(Params params) {
        Order order = params.order;
        Menu menu = params.menu;

        order.getMenus().add(menu);

        return dataSource.updateOrder(order).toObservable();
    }

    public static final class Params {
        private final Order order;
        private final Menu menu;

        public Params(Order order, Menu menu) {
            this.order = order;
            this.menu = menu;
        }
    }
}
