package com.sangwoo.possystem.ui.main.admin.employee;

import com.sangwoo.possystem.ui.BasePanel;

import javax.swing.*;
import java.awt.*;

public class EmployeeTab extends BasePanel {

    private JLabel employeeLabel;
    private JTextField employeeTextField;
    private JButton registerButton;
    private JButton inquiryButton;
    private JTextArea viewArea;

    @Override
    public void initView() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        employeeLabel = new JLabel("직원명");
        employeeTextField = new JTextField();
        registerButton = new JButton("직원등록");
        inquiryButton = new JButton("조회");
        viewArea = new JTextArea();

        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(20, 0, 0, 0);
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = 1;
        c.weighty = 0;
        add(employeeLabel, c);

        c.gridy = 1;
        c.insets = new Insets(0, 0, 0, 0);
        c.ipadx = 60;
        c.weighty = 0;
        add(employeeTextField, c);

        c.gridx = 1;
        c.ipadx = 0;
        c.anchor = GridBagConstraints.NORTHEAST;
        add(registerButton, c);

        c.gridx = 2;
        c.weightx = 0;
        add(inquiryButton, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        add(viewArea, c);
    }

    @Override
    public void inject() {

    }

    @Override
    public void setTitle(String title) { /* Do nothing */ }
}
