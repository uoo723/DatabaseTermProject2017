package com.sangwoo.possystem.ui.main.order;

import javax.inject.Inject;

// TODO: 2017. 6. 11. Impl OrderPresenter
public class OrderPresenter implements OrderContract.Presenter {

    private OrderContract.View view;

    @Inject
    OrderPresenter() {
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
}
