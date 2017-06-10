package com.sangwoo.possystem.ui.db_login;

import com.sangwoo.possystem.App;
import com.sangwoo.possystem.common.utils.SwingUtils;
import com.sangwoo.possystem.common.widgets.Toast;
import com.sangwoo.possystem.ui.BaseFrame;
import com.sangwoo.possystem.ui.main.MainView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;

public class DBLoginView extends BaseFrame implements DBLoginContract.View {

    private static final Logger logger = LogManager.getLogger();

    @Inject
    DBLoginContract.Presenter presenter;

    private JLabel hostLabel;
    private JLabel idLabel;
    private JLabel pwdLabel;

    private JTextField hostInput;
    private JTextField idInput;
    private JPasswordField pwdInput;

    private JButton loginButton;

    @Override
    public void inject() {
        logger.info("inject");

        DaggerDBLoginComponent
                .builder()
                .appComponent(App.getAppComponent())
                .dBLoginPresenterModule(new DBLoginPresenterModule())
                .build()
                .inject(this);
    }

    @Override
    public void initView() {
        presenter.bindView(this);

        hostLabel = new JLabel("호스트");
        idLabel = new JLabel("아이디");
        pwdLabel = new JLabel("비밀번호");

        hostInput = new JTextField();
        idInput = new JTextField();
        pwdInput = new JPasswordField();

        loginButton = new JButton("로그인");

        setResizable(false);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 10, 0, 10);
        add(hostLabel, c);

        c.gridx = 0;
        c.gridy = 1;
        add(idLabel, c);

        c.gridx = 0;
        c.gridy = 2;
        add(pwdLabel, c);

        c.gridx = 1;
        c.gridy = 0;
        c.ipadx = 40;
        c.insets = new Insets(0, 0, 0, 10);
        hostInput.setText("localhost");
        add(hostInput, c);

        c.gridx = 1;
        c.gridy = 1;
        c.ipadx = 100;
        add(idInput, c);

        c.gridx = 1;
        c.gridy = 2;
        add(pwdInput, c);

        c.gridx = 2;
        c.gridy = 0;
        c.gridheight = 3;
        c.ipadx = 40;
        add(loginButton, c);

        setFocusTraversalPolicy(new FocusTraversalPolicy() {

            @SuppressWarnings("Duplicates")
            @Override
            public Component getComponentAfter(Container aContainer, Component aComponent) {
                if (aComponent == hostInput)
                    return idInput;
                else if (aComponent == idInput)
                    return pwdInput;
                else if (aComponent == pwdInput)
                    return loginButton;
                else
                    return hostInput;
            }

            @SuppressWarnings("Duplicates")
            @Override
            public Component getComponentBefore(Container aContainer, Component aComponent) {
                if (aComponent == loginButton)
                    return pwdInput;
                else if (aComponent == pwdInput)
                    return idInput;
                else if (aComponent == idInput)
                    return hostInput;
                else
                    return loginButton;
            }

            @Override
            public Component getFirstComponent(Container aContainer) {
                return hostInput;
            }

            @Override
            public Component getLastComponent(Container aContainer) {
                return loginButton;
            }

            @Override
            public Component getDefaultComponent(Container aContainer) {
                return idInput;
            }
        });

        pack();
        SwingUtils.ceneterWindow(this);

        initButtonListener();

        presenter.login("192.168.56.101", "possystem", "possystem");
    }

    @Override
    public void loginResult(boolean succeed) {
        if (succeed) {
            dispose();
            System.out.println("succeed");
            new MainView();
        } else {
            Toast.makeToast(this, " 로그인 실패 ");
        }
    }

    private void initButtonListener() {
        loginButton.addActionListener(e ->
                presenter.login(hostInput.getText(), idInput.getText(),
                        String.valueOf(pwdInput.getPassword())));
    }
}
