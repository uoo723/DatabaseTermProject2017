package com.sangwoo.possystem.ui.main.admin.customer;

import com.sangwoo.possystem.ui.BasePanel;

import javax.swing.*;

public class CustomerTab extends BasePanel {

    private JLabel testLabel;

    @Override
    public void initView() {
        testLabel = new JLabel("고객");
        add(testLabel);
    }

    @Override
    public void inject() {

    }

    @Override
    public void setTitle(String title) {

    }
}
