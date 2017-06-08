package com.sangwoo.possystem.ui;

import javax.swing.*;

public abstract class BaseFrame extends JFrame implements BaseView, ComponentInjector {
    public BaseFrame() {
        super();
        inject();
        initView();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
