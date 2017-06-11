package com.sangwoo.possystem.ui.main.menu;

import com.sangwoo.possystem.ui.BasePanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MenuView extends BasePanel {

    private static final Logger logger = LogManager.getLogger();

    private JLabel titleLabel;
    private JPanel contentPanel;
    private List<JButton> menuButtons;

    @Override
    public void initView() {
        setLayout(new BorderLayout());

        titleLabel = new JLabel("메뉴");
        contentPanel = new JPanel();

        Border padding = BorderFactory.createEmptyBorder(1, 1, 1, 1);
        Border lineBorder = BorderFactory.createLineBorder(Color.black);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(lineBorder, padding));
        contentPanel.setLayout(new GridBagLayout());

        initContent();
        initButtonListener();

        add(titleLabel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    @Override
    public void inject() {

    }

    @Override
    public void setTitle(String title) { /* Do nothing */ }

    private void initContent() {
        menuButtons = new ArrayList<>(20);
        final GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTH;
        IntStream.range(0, 20).forEach(i -> {
//            logger.info("c.girdx: " + c.gridx + ", c.gridy: " + c.gridy);
            if (i == 10) {
                c.gridx++;
                c.gridy = 0;
            }

            JButton button = new JButton((i + 1) + "");
            menuButtons.add(button);
            contentPanel.add(button, c);
            c.gridy++;
        });
    }

    private void initButtonListener() {
        // TODO: 2017. 6. 11. Impl initButtonListener
    }
}
