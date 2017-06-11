package com.sangwoo.possystem.ui.main.order;

import com.sangwoo.possystem.ui.BasePanel;

import javax.swing.*;
import java.awt.*;

public class OrderView extends BasePanel {

    public interface OnTableStateChangedListener {
        void onChanged(int tableNum, boolean order);
    }

    private OnTableStateChangedListener onTableStateChangedListener;

    private JLabel titleLabel;
    private JPanel contentPanel;

    @Override
    public void initView() {
        setLayout(new BorderLayout());

        titleLabel = new JLabel("주문내역");
        contentPanel = new JPanel();

        contentPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        add(titleLabel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    @Override
    public void inject() {

    }

    @Override
    public void setTitle(String title) {

    }

    public void setOnTableStateChangedListener(OnTableStateChangedListener listener) {
        onTableStateChangedListener = listener;
    }
}
