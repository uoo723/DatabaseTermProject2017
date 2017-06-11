package com.sangwoo.possystem.ui.main.admin.sales;

import com.sangwoo.possystem.ui.BasePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class SalesTab extends BasePanel {

    private JLabel termLabel;
    private JComboBox<Term> dateComboBox;
    private JTextArea viewArea;
    private JScrollPane scrollPane;

    @Override
    public void initView() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        termLabel = new JLabel("기간");
        dateComboBox = new JComboBox<>();
        viewArea = new JTextArea();
        scrollPane = new JScrollPane(viewArea);

        viewArea.setEditable(false);

        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(20, 0, 0, 0);
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = 0;
        c.weighty = 0;
        add(termLabel, c);

        c.gridx = 1;
        c.weightx = 1;
        c.insets = new Insets(16, 20, 0, 0);
        add(dateComboBox, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.weighty = 1;
        c.insets = new Insets(10, 0, 0, 0);
        c.fill = GridBagConstraints.BOTH;
        add(scrollPane, c);

        testDate();
    }

    @Override
    public void inject() {

    }

    @Override
    public void setTitle(String title) { /* Do nothing */ }

    private void testDate() {
        dateComboBox.addItem(new Term(Calendar.getInstance().getTime()));
    }
}
