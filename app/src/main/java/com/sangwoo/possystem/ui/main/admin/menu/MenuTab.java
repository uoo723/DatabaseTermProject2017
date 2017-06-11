package com.sangwoo.possystem.ui.main.admin.menu;

import com.sangwoo.possystem.ui.BasePanel;

import javax.swing.*;

public class MenuTab extends BasePanel {

    private JLabel testLabel;

    @Override
    public void initView() {
        testLabel = new JLabel("메뉴");
        add(testLabel);
    }

    @Override
    public void inject() {

    }

    @Override
    public void setTitle(String title) {

    }
}
