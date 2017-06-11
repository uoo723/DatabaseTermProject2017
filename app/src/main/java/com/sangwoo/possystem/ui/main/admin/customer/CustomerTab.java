package com.sangwoo.possystem.ui.main.admin.customer;

import com.sangwoo.possystem.ui.BasePanel;

import javax.swing.*;
import java.awt.*;

public class CustomerTab extends BasePanel {

    private JLabel customerLabel;
    private JTextField customerTextField;
    private JButton joinButton;
    private JButton inquiryButton;
    private JScrollPane scrollPane;
    private JTextArea viewArea;

    @Override
    public void initView() {

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        customerLabel = new JLabel("고객명");
        customerTextField = new JTextField();
        joinButton = new JButton("가입");
        inquiryButton = new JButton("조회");
        viewArea = new JTextArea();
        scrollPane = new JScrollPane(viewArea);

        viewArea.setEditable(false);

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 0;
        c.anchor = GridBagConstraints.NORTHWEST;
        add(customerLabel, c);

        c.gridy = 1;
        c.ipadx = 60;
        c.weightx = 0;
        c.weighty = 0;
        add(customerTextField, c);

        c.gridx = 1;
        c.ipadx = 0;
        c.weightx = 1;
        c.anchor = GridBagConstraints.NORTHEAST;
        add(joinButton, c);

        c.gridx = 2;
        c.weightx = 0;
        add(inquiryButton, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        add(scrollPane, c);
    }

    @Override
    public void inject() {

    }

    @Override
    public void setTitle(String title) { /* Do nothing */ }
}
