package com.sangwoo.possystem.ui.main;

import com.sangwoo.possystem.App;
import com.sangwoo.possystem.common.utils.SwingUtils;
import com.sangwoo.possystem.common.widgets.Toast;
import com.sangwoo.possystem.ui.BaseFrame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.swing.*;
import java.io.File;

public class MainView extends BaseFrame implements MainContract.View {

    private static final Logger logger = LogManager.getLogger();

    @Inject
    MainContract.Presenter presenter;

    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem openMenuItem;
    private JMenuItem loginMenuItem;

    private JFileChooser fileChooser;

    private LoginPrompt loginPrompt;

    @Override
    public void inject() {
        DaggerMainComponent.builder()
                .appComponent(App.getAppComponent())
                .mainPresenterModule(new MainPresenterModule())
                .build()
                .inject(this);
    }

    @Override
    public void initView() {
        presenter.bindView(this);

        fileChooser = new JFileChooser();
        loginPrompt = new LoginPrompt();

        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        openMenuItem = new JMenuItem("Open");
        loginMenuItem = new JMenuItem("Log in");

        menu.add(openMenuItem);
        menu.add(loginMenuItem);
        menuBar.add(menu);

        setJMenuBar(menuBar);

        initButtonListener();

        setSize(100, 100);
        SwingUtils.ceneterWindow(this);
    }

    @Override
    public void succeedLoadingData() {

    }

    @Override
    public void failedLoadingData() {

    }

    @Override
    public void succeedEmployeeLogin() {

    }

    @Override
    public void failedEmployeeLogin() {
        Toast.makeToast(this, "  로그인 실패  ");
    }

    private void initButtonListener() {
        openMenuItem.addActionListener(e -> openChooser());
        loginMenuItem.addActionListener(e -> openLoginPrompt());
        loginPrompt.setOnLoginListener(presenter::employeeLogin);
    }

    private void openChooser() {
        int returnVal = fileChooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            logger.info("file path: " + file.getPath());
            presenter.loadData(file);
        } else {
            logger.info("Open command cancelled by user");
        }
    }

    private void openLoginPrompt() {
        loginPrompt.setVisible(true);
    }
}
