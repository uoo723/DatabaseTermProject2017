package com.sangwoo.possystem.ui.main;

import com.sangwoo.possystem.App;
import com.sangwoo.possystem.common.utils.SwingUtils;
import com.sangwoo.possystem.common.widgets.Toast;
import com.sangwoo.possystem.ui.BaseFrame;
import com.sangwoo.possystem.ui.main.admin.AdminView;
import com.sangwoo.possystem.ui.main.menu.MenuView;
import com.sangwoo.possystem.ui.main.order.OrderView;
import com.sangwoo.possystem.ui.main.table.TableView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;

public class MainView extends BaseFrame implements MainContract.View {

    private static final Logger logger = LogManager.getLogger();
    private static final int WIDTH = 600;
    private static final int HEIGHT = 700;

    @Inject
    MainContract.Presenter presenter;

    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem openMenuItem;
    private JMenuItem loginMenuItem;

    private JPanel mainContainer;

    private JLabel titleLabel;

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

        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());

        setMenu();
        setMainContainer();

        initButtonListener();

        SwingUtils.ceneterWindow(this);
    }

    @Override
    public void succeedLoadingData() {
        Toast.makeToast(this, "  데이터 불러오기 성공  ");
    }

    @Override
    public void failedLoadingData() {
        Toast.makeToast(this, "  데이터 불러오기 실패  ");
    }

    @Override
    public void succeedEmployeeLogin() {
        Toast.makeToast(this, "  로그인 성공  ");
        loginPrompt.hidePrompt();
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
        loginPrompt.showPrompt();
        loginPrompt.setOnLoginListener(presenter::employeeLogin);
    }

    private void setMenu() {
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        openMenuItem = new JMenuItem("Open");
        loginMenuItem = new JMenuItem("Log in");

        menu.add(openMenuItem);
        menu.add(loginMenuItem);
        menuBar.add(menu);

        setJMenuBar(menuBar);
    }

    private void setMainContainer() {
        TableView tableView = new TableView();
        OrderView orderView = new OrderView();
        MenuView menuView = new MenuView();
        AdminView adminView = new AdminView();

        tableView.setParentJFrame(this);
        orderView.setParentJFrame(this);
        menuView.setParentJFrame(this);
        adminView.setParentJFrame(this);

        tableView.setPreferredSize(new Dimension(WIDTH / 3, 0));
        orderView.setPreferredSize(new Dimension(WIDTH / 3, 0));
        menuView.setPreferredSize(new Dimension(WIDTH / 3, 0));
        adminView.setPreferredSize(new Dimension(WIDTH / 3, 0));

        mainContainer = new JPanel();
        mainContainer.setLayout(new GridBagLayout());
        mainContainer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = .5;
        c.fill = GridBagConstraints.BOTH;

        titleLabel = new JLabel("식당 주문관리", SwingConstants.CENTER);
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.PLAIN, 40));
        Border padding = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        Border lineBorder = BorderFactory.createLineBorder(Color.black);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(padding, lineBorder));

        add(titleLabel, BorderLayout.NORTH);
        add(mainContainer, BorderLayout.CENTER);

        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 0, 0, 5);
        c.weighty = .4;
        mainContainer.add(tableView, c);

        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(10, 5, 0, 0);
        mainContainer.add(orderView, c);

        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(10, 0, 0, 5);
        c.weighty = .6;
        mainContainer.add(menuView, c);

        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(10, 5, 0, 0);
        mainContainer.add(adminView, c);

        menuView.setOnMenuClickListener(orderView::addMenu);
    }
}
