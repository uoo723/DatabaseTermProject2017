package com.sangwoo.possystem.ui.main;

import com.sangwoo.possystem.common.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class LoginPrompt extends JFrame {
    private OnLoginListener listener;

    private JLabel nameLabel;
    private JLabel idLabel;

    private JTextField nameInput;
    private JTextField idInput;

    private JButton loginButton;

    public interface OnLoginListener {
        void onLogin(String name, String id);
    }

    public LoginPrompt() {
        super();

        initView();
    }

    public void setOnLoginListener(OnLoginListener listener) {
        this.listener = listener;
    }

    private void initView() {
        setTitle("사원로그인");
        setResizable(false);

        nameLabel = new JLabel("이름");
        idLabel = new JLabel("사원번호");
        loginButton = new JButton("로그인");

        nameInput = new JTextField();
        idInput = new JTextField();

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 10, 0, 10);
        add(nameLabel, c);

        c.gridx = 0;
        c.gridy = 1;
        add(idLabel, c);

        c.gridx = 1;
        c.gridy = 0;
        c.ipadx = 100;
        c.insets = new Insets(0, 0, 0, 10);
        add(nameInput, c);

        c.gridx = 1;
        c.gridy = 1;
        add(idInput, c);

        c.gridx = 2;
        c.gridy = 0;
        c.gridheight = 2;
        c.ipadx = 40;
        c.insets = new Insets(0, 0, 0, 0);
        add(loginButton, c);

        setFocusTraversalPolicy(new FocusTraversalPolicy() {

            @SuppressWarnings("Duplicates")
            @Override
            public Component getComponentAfter(Container aContainer, Component aComponent) {
                if (aComponent == nameInput)
                    return idInput;
                else if (aComponent == idInput)
                    return loginButton;
                else
                    return nameInput;
            }

            @SuppressWarnings("Duplicates")
            @Override
            public Component getComponentBefore(Container aContainer, Component aComponent) {
                if (aComponent == loginButton)
                    return idInput;
                else if (aComponent == idInput)
                    return nameInput;
                else
                    return loginButton;
            }

            @Override
            public Component getFirstComponent(Container aContainer) {
                return nameInput;
            }

            @Override
            public Component getLastComponent(Container aContainer) {
                return loginButton;
            }

            @Override
            public Component getDefaultComponent(Container aContainer) {
                return nameInput;
            }
        });

        pack();
        SwingUtils.ceneterWindow(this);

        initButtonListener();
    }

    private void initButtonListener() {
        loginButton.addActionListener(e -> {
            String name = nameInput.getText();
            String id = idInput.getText();

            if (listener != null)
                listener.onLogin(name, id);
        });
    }
}
