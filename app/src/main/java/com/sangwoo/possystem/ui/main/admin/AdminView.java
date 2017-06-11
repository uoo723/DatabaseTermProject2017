package com.sangwoo.possystem.ui.main.admin;

import com.sangwoo.possystem.ui.BasePanel;
import com.sangwoo.possystem.ui.main.admin.customer.CustomerTab;
import com.sangwoo.possystem.ui.main.admin.employee.EmployeeTab;
import com.sangwoo.possystem.ui.main.admin.menu.MenuTab;
import com.sangwoo.possystem.ui.main.admin.sales.SalesTab;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class AdminView extends BasePanel {

    private JLabel titleLabel;
    private JPanel contentPanel;

    private JTabbedPane tabbedPane;

    @Override
    public void initView() {
        setLayout(new BorderLayout());

        titleLabel = new JLabel("등록/조회");
        contentPanel = new JPanel();

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

    private void initContent() {
        tabbedPane = new JTabbedPane();

        CustomerTab customerTab = new CustomerTab();
        SalesTab salesTab = new SalesTab();
        EmployeeTab employeeTab = new EmployeeTab();
        MenuTab menuTab = new MenuTab();

        customerTab.setParentJFrame(getParentJFrame());
        salesTab.setParentJFrame(getParentJFrame());
        employeeTab.setParentJFrame(getParentJFrame());
        menuTab.setParentJFrame(getParentJFrame());

        tabbedPane.addTab("고객", customerTab);
        tabbedPane.addTab("매출", salesTab);
        tabbedPane.addTab("직원", employeeTab);
        tabbedPane.addTab("메뉴", menuTab);

        contentPanel.add(tabbedPane);
    }
}
