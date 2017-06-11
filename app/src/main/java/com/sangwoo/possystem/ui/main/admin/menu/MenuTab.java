package com.sangwoo.possystem.ui.main.admin.menu;

import com.sangwoo.possystem.ui.BasePanel;
import com.sangwoo.possystem.ui.main.admin.RegisterPrompt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class MenuTab extends BasePanel {

    private static final Logger logger = LogManager.getLogger();

    private JLabel menuLabel;
    private JTextField menuTextField;
    private JButton registerButton;
    private JButton inquiryButton;
    private JTextArea viewArea;

    private RegisterPrompt<String> prompt;

    @Override
    public void initView() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        initPrompt();

        menuLabel = new JLabel("메뉴명");
        menuTextField = new JTextField();
        registerButton = new JButton("메뉴등록");
        inquiryButton = new JButton("조회");
        viewArea = new JTextArea();

        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(20, 0, 0, 0);
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = 1;
        c.weighty = 0;
        add(menuLabel, c);

        c.gridy = 1;
        c.insets = new Insets(0, 0, 0, 0);
        c.ipadx = 100;
        c.weighty = 0;
        add(menuTextField, c);

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

        initButtonListener();
    }

    @Override
    public void inject() {

    }

    @Override
    public void setTitle(String title) { /* Do nothing */ }

    private void initPrompt() {
        JTextField textField = new JTextField();
        prompt = new RegisterPrompt<>();
        prompt.setValueComponent(textField);
        prompt.initPrompt("메뉴등록", "메뉴명", "가격", String.class);
    }

    private void initButtonListener() {
        registerButton.addActionListener(e -> prompt.showPrompt());
        prompt.setOnRegisterListener((name, value) -> {
            // TODO: 2017. 6. 11. Impl RegisterPrompt#setOnRegisterListener
            logger.info("name: " + name + ", value: " + value);
        });
    }
}
