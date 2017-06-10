package com.sangwoo.possystem.ui;

import javax.swing.*;

public abstract class BasePanel extends JPanel implements BaseView, ComponentInjector {
    public BasePanel() {
        super();
        inject();
        initView();
    }
}
