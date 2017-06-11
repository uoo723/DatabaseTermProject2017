package com.sangwoo.possystem.ui.main.admin.sales;

import com.sangwoo.possystem.ui.BasePanel;

import javax.swing.*;

public class SalesTab extends BasePanel {

    private JLabel testLabel;

    @Override
    public void initView() {
        testLabel = new JLabel("매출");
        add(testLabel);
    }

    @Override
    public void inject() {

    }

    @Override
    public void setTitle(String title) {

    }
}
