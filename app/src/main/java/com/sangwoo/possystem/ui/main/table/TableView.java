package com.sangwoo.possystem.ui.main.table;

import com.sangwoo.possystem.ui.BasePanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class TableView extends BasePanel {

    private static final Logger logger = LogManager.getLogger();

    private JLabel titleLabel;
    private JPanel contentPanel;
    private List<JLabel> tables;

    @Override
    public void initView() {
        setLayout(new BorderLayout());

        titleLabel = new JLabel("테이블 현황");
        contentPanel = new JPanel();

        contentPanel.setLayout(new GridLayout(4, 5, 10, 10));

        Border padding = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        Border lineBorder = BorderFactory.createLineBorder(Color.black);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(lineBorder, padding));
        initTables();

        add(titleLabel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    @Override
    public void inject() { /* Do nothing */ }

    @Override
    public void setTitle(String title) { /* Do nothing */ }

    private void initTables() {
        tables = new ArrayList<>(20);
        for (int i = 1; i <= 20; i++) {
            JLabel label = new JLabel(i + "", SwingConstants.CENTER);
            label.setPreferredSize(new Dimension(5, 5));
            label.setOpaque(true);
            label.setBorder(BorderFactory.createLineBorder(Color.black, 2));
            label.setBackground(Color.white);
            tables.add(label);
        }

        tables.forEach(contentPanel::add);
    }

    public void setOrder(int tableNum, boolean order) {
        if (order) {
            tables.get(tableNum - 1).setBackground(Color.yellow);
        } else {
            tables.get(tableNum - 1).setBackground(Color.white);
        }
    }
}
