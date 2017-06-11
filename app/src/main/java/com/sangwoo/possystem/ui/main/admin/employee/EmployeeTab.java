package com.sangwoo.possystem.ui.main.admin.employee;

import com.sangwoo.possystem.App;
import com.sangwoo.possystem.common.widgets.Toast;
import com.sangwoo.possystem.domain.model.Employee;
import com.sangwoo.possystem.ui.BasePanel;
import com.sangwoo.possystem.ui.main.admin.RegisterPrompt;
import com.sangwoo.possystem.ui.main.admin.customer.DaggerCustomerTabComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;

public class EmployeeTab extends BasePanel implements EmployeeTabContract.View {

    private static final Logger logger = LogManager.getLogger();

    private JLabel employeeLabel;
    private JTextField employeeTextField;
    private JButton registerButton;
    private JButton inquiryButton;
    private JTextArea viewArea;
    private RegisterPrompt<Employee.Rank> prompt;

    @Inject
    EmployeeTabContract.Presenter presenter;

    @Override
    public void initView() {
        presenter.bindView(this);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        initPrompt();

        employeeLabel = new JLabel("직원명");
        employeeTextField = new JTextField();
        registerButton = new JButton("직원등록");
        inquiryButton = new JButton("조회");
        viewArea = new JTextArea();

        viewArea.setEditable(false);

        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(20, 0, 0, 0);
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = 1;
        c.weighty = 0;
        add(employeeLabel, c);

        c.gridy = 1;
        c.insets = new Insets(0, 0, 0, 0);
        c.ipadx = 60;
        c.weighty = 0;
        add(employeeTextField, c);

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
        DaggerEmployeeTabComponent.builder()
                .appComponent(App.getAppComponent())
                .employeeTabPresenterModule(new EmployeeTabPresenterModule())
                .build()
                .inject(this);
    }

    @Override
    public void setTitle(String title) { /* Do nothing */ }

    @Override
    public void requiredEmployeeLogin() {
        Toast.makeToast(getParentJFrame(), "  직원 로그인을 해주세요  ");
    }

    @Override
    public void showEmployeeInfo(final Employee employee) {
        viewArea.setText(getEmployeeInfoString(employee));
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
        if (permission) {
            prompt.showPrompt();
        } else {
            Toast.makeToast(getParentJFrame(), "  권한이 없습니다  ");
        }
    }

    @Override
    public void succeedRegistration(String message) {
        if (message == null)
            Toast.makeToast(getParentJFrame(), "  등록 성공  ");
        else
            Toast.makeToast(getParentJFrame(), message);
        prompt.hidePrompt();
    }

    @Override
    public void failedRegistration(String message) {
        if (message == null)
            Toast.makeToast(getParentJFrame(), "  등록 실패  ");
        else
            Toast.makeToast(getParentJFrame(), message);
    }

    private void initPrompt() {
        Employee.Rank[] ranks = {Employee.Rank.SUPERVISOR, Employee.Rank.STAFF};
        JComboBox<Employee.Rank> comboBox = new JComboBox<>(ranks);

        prompt = new RegisterPrompt<>();
        prompt.setValueComponent(comboBox);
        prompt.initPrompt("직원등록", "직원명", "직급", Employee.Rank.class);
    }

    private void initButtonListener() {
        registerButton.addActionListener(e -> presenter.registerClicked());
        inquiryButton.addActionListener(e -> presenter.inquiry(employeeTextField.getText()));
        prompt.setOnRegisterListener(presenter::register);
    }

    private String getEmployeeInfoString(final Employee employee) {
        return String.format("직원명:%s\n직급:%s\n총실적:%d", employee.getName(), employee.getRank(), employee.getRecord());
    }
}
