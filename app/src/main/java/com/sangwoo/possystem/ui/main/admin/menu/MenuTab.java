package com.sangwoo.possystem.ui.main.admin.menu;

import com.sangwoo.possystem.App;
import com.sangwoo.possystem.common.widgets.Toast;
import com.sangwoo.possystem.domain.model.Menu;
import com.sangwoo.possystem.ui.BasePanel;
import com.sangwoo.possystem.ui.main.admin.RegisterPrompt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;

public class MenuTab extends BasePanel implements MenuTabContract.View {

    public interface OnMenuAddListener {
        void refresh();
    }

    private static final Logger logger = LogManager.getLogger();

    private OnMenuAddListener listener;

    private JLabel menuLabel;
    private JTextField menuTextField;
    private JButton registerButton;
    private JButton inquiryButton;
    private JTextArea viewArea;

    private RegisterPrompt<String> prompt;

    @Inject
    MenuTabContract.Presenter presenter;


    @Override
    public void initView() {
        presenter.bindView(this);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        initPrompt();

        menuLabel = new JLabel("메뉴명");
        menuTextField = new JTextField();
        registerButton = new JButton("메뉴등록");
        inquiryButton = new JButton("조회");
        viewArea = new JTextArea();

        viewArea.setEditable(false);

        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(20, 0, 0, 0);
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = 1;
        c.weighty = 0;
        add(menuLabel, c);

        c.gridy = 1;
        c.insets = new Insets(0, 0, 0, 0);
        c.ipadx = 100;
        c.weighty = 0;
        add(menuTextField, c);

        c.gridx = 1;
        c.ipadx = 0;
        c.anchor = GridBagConstraints.NORTHEAST;
        add(registerButton, c);

        c.gridx = 2;
        c.weightx = 0;
        add(inquiryButton, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        add(viewArea, c);

        initButtonListener();
    }

    @Override
    public void inject() {
        DaggerMenuTabComponent.builder()
                .appComponent(App.getAppComponent())
                .menuTabPresenterModule(new MenuTabPresenterModule())
                .build()
                .inject(this);
    }

    @Override
    public void setTitle(String title) { /* Do nothing */ }

    @Override
    public void requiredEmployeeLoin() {
        Toast.makeToast(getParentJFrame(), "  직원 로그인을 해주세요  ");
    }

    @Override
    public void succeedRegistration() {
        if (listener != null)
            listener.refresh();

        Toast.makeToast(getParentJFrame(), "  등록 성공  ");
        prompt.hidePrompt();
    }

    @Override
    public void failedRegistration(String message) {
        if (message == null)
            Toast.makeToast(getParentJFrame(), " 등록 실패  ");
        else
            Toast.makeToast(getParentJFrame(), message);
    }

    @Override
    public void showMenuInfo(final Menu menu) {
        viewArea.setText(getMenuInfoString(menu));
    }

    @Override
    public void failedInquiry(String message) {
        if (message == null)
            Toast.makeToast(getParentJFrame(), "  조회 실패  ");
        else
            Toast.makeToast(getParentJFrame(), message);
    }

    @Override
    public void registerClickedResult(boolean permission) {
        if (permission)
            prompt.showPrompt();
        else
            Toast.makeToast(getParentJFrame(), "  권한 없음  ");
    }

    public void setOnMenuAddListener(OnMenuAddListener listener) {
        this.listener = listener;
    }

    private void initPrompt() {
        JTextField textField = new JTextField();
        prompt = new RegisterPrompt<>();
        prompt.setValueComponent(textField);
        prompt.initPrompt("메뉴등록", "메뉴명", "가격", String.class);
    }

    private void initButtonListener() {
        registerButton.addActionListener(e -> presenter.registerClicked());
        inquiryButton.addActionListener(e -> presenter.inquiry(menuTextField.getText()));
        prompt.setOnRegisterListener(presenter::register);
    }

    private String getMenuInfoString(final Menu menu) {
        return String.format("메뉴명:%s\n가격:%d", menu.getName(), menu.getPrice());
    }
}
