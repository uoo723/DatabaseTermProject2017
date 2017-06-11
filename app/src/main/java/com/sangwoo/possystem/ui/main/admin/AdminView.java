package com.sangwoo.possystem.ui.main.admin;

import com.sangwoo.possystem.ui.BasePanel;
import com.sangwoo.possystem.ui.main.admin.customer.CustomerTab;
import com.sangwoo.possystem.ui.main.admin.employee.EmployeeTab;
import com.sangwoo.possystem.ui.main.admin.menu.MenuTab;
import com.sangwoo.possystem.ui.main.admin.sales.SalesTab;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class AdminView extends BasePanel {

    private static final Logger logger = LogManager.getLogger();

    private JLabel titleLabel;
    private JPanel contentPanel;

    private JTabbedPane tabbedPane;

    private CustomerTab customerTab;
    private SalesTab salesTab;
    private EmployeeTab employeeTab;
    private MenuTab menuTab;

    @Override
    public void initView() {
        setLayout(new BorderLayout());

        titleLabel = new JLabel("등록/조회");
        contentPanel = new JPanel();

        customerTab = new CustomerTab();
        salesTab = new SalesTab();
        employeeTab = new EmployeeTab();
        menuTab = new MenuTab();

        Border padding = BorderFactory.createEmptyBorder(1, 1, 1, 1);
        Border lineBorder = BorderFactory.createLineBorder(Color.black);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(lineBorder, padding));
        contentPanel.setLayout(new GridLayout(1, 1));
        initContent();

        add(titleLabel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    @Override
    public void inject() { /* Do nothing */ }

    @Override
    public void setTitle(String title) { /* Do nothing */ }

    @Override
    public void setParentJFrame(JFrame parent) {
        super.setParentJFrame(parent);

        customerTab.setParentJFrame(parent);
        salesTab.setParentJFrame(parent);
        employeeTab.setParentJFrame(parent);
        menuTab.setParentJFrame(parent);
    }

    public MenuTab getMenuTab() {
        return menuTab;
    }

    private void initContent() {
        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("고객", customerTab);
        tabbedPane.addTab("매출", salesTab);
        tabbedPane.addTab("직원", employeeTab);
        tabbedPane.addTab("메뉴", menuTab);

        contentPanel.add(tabbedPane);
    }
}
