package com.sangwoo.possystem.ui.main.admin.customer;

import com.sangwoo.possystem.common.utils.SwingUtils;
import com.sangwoo.possystem.domain.model.Customer;
import com.sangwoo.possystem.ui.Prompt;

import javax.swing.*;
import java.awt.*;

public class CustomerRegisterPrompt extends JFrame implements Prompt {
    public interface OnRegisterListener {
        void onRegister(String name, String birth, String phoneNum, Customer.Level level);
    }

    private OnRegisterListener listener;

    private JLabel nameLabel;
    private JLabel birthLabel;
    private JLabel phoneLabel;
    private JLabel levelLabel;

    private JTextField nameTextField;
    private JTextField birthTextField;
    private JTextField phoneTextField;

    private JButton registerButton;
    private JButton cancelButton;

    private JComboBox<Customer.Level> levelJComboBox;

    public CustomerRegisterPrompt() {
        super();
        initView();
    }

    @Override
    public void showPrompt() {
        nameTextField.setText("");
        birthTextField.setText("");
        phoneTextField.setText("");
        levelJComboBox.setSelectedIndex(0);
        setVisible(true);
    }

    @Override
    public void hidePrompt() {
        dispose();
    }

    public void setOnRegisterListener(OnRegisterListener listener) {
        this.listener = listener;
    }

    private void initView() {
        setLayout(new GridBagLayout());
        setTitle("회원등록");
        setResizable(false);
        GridBagConstraints c = new GridBagConstraints();

        nameLabel = new JLabel("고객명");
        birthLabel = new JLabel("생일(4자리)");
        phoneLabel = new JLabel("연락처");
        levelLabel = new JLabel("등급");

        nameTextField = new JTextField();
        birthTextField = new JTextField();
        phoneTextField = new JTextField();

        registerButton = new JButton("가입신청");
        cancelButton = new JButton("취소");

        Customer.Level[] levels = {Customer.Level.GOLD, Customer.Level.SILVER, Customer.Level.BRONZE,
                Customer.Level.NORMAL};

        levelJComboBox = new JComboBox<>(levels);

        c.gridx = 0;
        c.gridy = 0;
        add(nameLabel, c);

        c.gridy = 1;
        add(birthLabel, c);

        c.gridy = 2;
        add(phoneLabel, c);

        c.gridy = 3;
        add(levelLabel, c);

        c.gridx = 1;
        c.gridy = 0;
        c.ipadx = 100;
        add(nameTextField, c);

        c.gridy = 1;
        add(birthTextField, c);

        c.gridy = 2;
        add(phoneTextField, c);

        c.gridy = 3;
        add(levelJComboBox, c);

        c.gridx = 0;
        c.gridy = 4;
        add(registerButton, c);

        c.gridx = 1;
        add(cancelButton, c);

        initButtonListener();

        pack();
        SwingUtils.ceneterWindow(this);
    }

    private void initButtonListener() {
        registerButton.addActionListener(e -> {
            if (listener != null)
                listener.onRegister(nameTextField.getText(), birthTextField.getText(),
                        phoneTextField.getText(), (Customer.Level) levelJComboBox.getSelectedItem());
        });

        cancelButton.addActionListener(e -> hidePrompt());
    }
}
