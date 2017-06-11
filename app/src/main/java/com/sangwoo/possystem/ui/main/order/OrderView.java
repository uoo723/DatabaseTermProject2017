package com.sangwoo.possystem.ui.main.order;

import com.sangwoo.possystem.App;
import com.sangwoo.possystem.common.widgets.Toast;
import com.sangwoo.possystem.ui.BasePanel;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;

public class OrderView extends BasePanel implements OrderContract.View {

    public interface OnTableStateChangedListener {
        void onChanged(int tableNum, boolean order);
    }

    private OnTableStateChangedListener onTableStateChangedListener;

    private JLabel titleLabel;
    private JPanel contentPanel;

    @Inject
    OrderContract.Presenter presenter;

    @Override
    public void initView() {
        presenter.bindView(this);

        setLayout(new BorderLayout());

        titleLabel = new JLabel("주문내역");
        contentPanel = new JPanel();

        contentPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        add(titleLabel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    @Override
    public void inject() {
        DaggerOrderComponent.builder()
                .appComponent(App.getAppComponent())
                .orderPresenterModule(new OrderPresenterModule())
                .build()
                .inject(this);
    }

    @Override
    public void setTitle(String title) { /* Do nothing */ }

    @Override
    public void succeedOrder() {
        Toast.makeToast(getParentJFrame(), "  주문 성공  ");
    }

    @Override
    public void failedOrder() {
        Toast.makeToast(getParentJFrame(), "  주문 실패  ");
    }

    @Override
    public void succeedCancel() {
        Toast.makeToast(getParentJFrame(), "  주문 취소  ");
    }

    @Override
    public void failedCancel() {
        Toast.makeToast(getParentJFrame(), "  주문 취소를 실패했습니다  ");
    }

    @Override
    public void succeedPay() {
        Toast.makeToast(getParentJFrame(), "  결제 성공  ");
        // TODO: 2017. 6. 11. reset view
    }

    @Override
    public void failedPay(String message) {
        if (message == null)
            Toast.makeToast(getParentJFrame(), "  결제 실패  ");
        else
            Toast.makeToast(getParentJFrame(), message);
    }

    public void setOnTableStateChangedListener(OnTableStateChangedListener listener) {
        onTableStateChangedListener = listener;
    }
}
