package com.sangwoo.possystem.ui.main.menu;

import com.sangwoo.possystem.ui.BasePanel;

import javax.swing.*;
import java.awt.*;

public class MenuView extends BasePanel {

    private JLabel titleLabel;
    private JPanel contentPanel;

    @Override
    public void initView() {
        setLayout(new BorderLayout());

        titleLabel = new JLabel("메뉴");
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
}
