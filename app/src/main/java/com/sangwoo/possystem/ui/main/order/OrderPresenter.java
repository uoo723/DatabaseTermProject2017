package com.sangwoo.possystem.ui.main.order;

import com.sangwoo.possystem.domain.model.Menu;
import com.sangwoo.possystem.domain.usecase.CancelOrder;
import com.sangwoo.possystem.domain.usecase.RequestOrder;
import com.sangwoo.possystem.domain.usecase.RequestPayment;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

// TODO: 2017. 6. 11. Impl OrderPresenter
public class OrderPresenter implements OrderContract.Presenter {

    private OrderContract.View view;
    private List<Menu> menuList = new ArrayList<>();

    private final RequestOrder requestOrderUseCase;
    private final CancelOrder cancelOrderUseCase;
    private final RequestPayment requestPaymentUseCase;

    @Inject
    OrderPresenter(RequestOrder requestOrder, CancelOrder cancelOrder, RequestPayment requestPayment) {
        requestOrderUseCase = requestOrder;
        cancelOrderUseCase = cancelOrder;
        requestPaymentUseCase = requestPayment;
    }

    @Override
    public void bindView(OrderContract.View view) {
        this.view = view;
    }

    @Override
    public void order(String customer, int tableNum) {

    }

    @Override
    public void cancel() {

    }

    @Override
    public void pay() {

    }

    @Override
    public void addMenu(Menu menu) {
        if (menuList.contains(menu)) {
            menuList.remove(menu);
        } else {
            menuList.add(menu);
        }

        view.showMenuList(getMenuList());
    }

    private String getMenuList() {
        final StringBuilder sb = new StringBuilder();
        final AtomicInteger total = new AtomicInteger(0);
        menuList.forEach(menu -> {
            sb.append(String.format("%s\t%d\n", menu.getName(), menu.getPrice()));
            total.addAndGet(menu.getPrice());
        });

        sb.append("\n\n\n");
        sb.append("---------------------\n");
        sb.append(String.format("총합계\t%d", total.get()));
        return sb.toString();
    }
}
