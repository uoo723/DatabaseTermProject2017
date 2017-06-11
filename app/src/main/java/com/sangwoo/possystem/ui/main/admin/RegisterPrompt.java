package com.sangwoo.possystem.ui.main.admin;

import com.sangwoo.possystem.common.exceptions.RuntimeSqlException;
import com.sangwoo.possystem.common.utils.SwingUtils;
import com.sangwoo.possystem.ui.Prompt;

import javax.swing.*;
import java.awt.*;

public class RegisterPrompt<V> extends JFrame implements Prompt {

    public interface OnRegisterListener<V> {
        void onRegister(String name, V value);
    }

    private OnRegisterListener<V> listener;

    private JLabel nameLabel;
    private JLabel valueLabel;

    private JTextField nameTextField;
    private JComponent valueComponent;

    private JButton registerButton;
    private JButton cancelButton;

    private Class<V> type;

    @Override
    public void showPrompt() {
        nameTextField.setText("");
        if (valueComponent instanceof JTextField)
            ((JTextField) valueComponent).setText("");
        setVisible(true);
    }

    @Override
    public void hidePrompt() {
        dispose();
    }

    public void setOnRegisterListener(OnRegisterListener<V> listener) {
        this.listener = listener;
    }

    public void setValueComponent(JComponent component) {
        valueComponent = component;
    }

    public void showPrompt(String title, String nameLabelStr, String valueLabelStr, Class<V> type) {
        this.type = type;

        if (valueComponent == null)
            throw new RuntimeException("setValueComponent must be set before initView()");

        setTitle(title);
        setResizable(false);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        nameLabel = new JLabel(nameLabelStr);
        valueLabel = new JLabel(valueLabelStr);
        nameTextField = new JTextField();
        registerButton = new JButton("등록");
        cancelButton = new JButton("취소");

        c.gridx = 0;
        c.gridy = 0;
        add(nameLabel, c);

        c.gridy = 1;
        add(valueLabel, c);

        c.gridx = 1;
        c.gridy = 0;
        c.ipadx = 100;
        add(nameTextField, c);

        c.gridy = 1;
        add(valueComponent, c);

        c.gridx = 0;
        c.gridy = 2;
        add(registerButton, c);

        c.gridx = 1;
        add(cancelButton, c);

        initButtonListener();

        pack();
        SwingUtils.ceneterWindow(this);
        showPrompt();
    }

    @SuppressWarnings("unchecked")
    private void initButtonListener() {
        registerButton.addActionListener(e -> {
            if (listener != null) {
                if (valueComponent instanceof JComboBox<?>) {
                    JComboBox<?> comboBox = (JComboBox<?>) valueComponent;
                    Object o = comboBox.getSelectedItem();
                    if (type.isInstance(o)) {
                        V v = (V) o;
                        listener.onRegister(nameTextField.getText(), v);
                    } else {
                        throw new RuntimeException("Type not matched");
                    }
                } else if (valueComponent instanceof JTextField) {
                    JTextField textField = (JTextField) valueComponent;
                    if (type.isInstance(textField.getText())) {
                        V v = (V) textField.getText();
                        listener.onRegister(nameTextField.getText(), v);
                    } else {
                        throw new RuntimeSqlException("Type not matched");
                    }
                } else {
                    throw new RuntimeException("Allow only valueComponent to be JTextField or JComboBox");
                }
            }
        });

        cancelButton.addActionListener(e -> hidePrompt());
    }
}
