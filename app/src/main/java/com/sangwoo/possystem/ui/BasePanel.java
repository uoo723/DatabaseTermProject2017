package com.sangwoo.possystem.ui;

import javax.swing.*;

public abstract class BasePanel extends JPanel implements BaseView, ComponentInjector {
    private JFrame parent;

    public BasePanel() {
        super();
        inject();
        initView();
    }

    public void setParentJFrame(JFrame parent) {
        this.parent = parent;
    }

    public JFrame getParentJFrame() {
        return parent;
    }
}
