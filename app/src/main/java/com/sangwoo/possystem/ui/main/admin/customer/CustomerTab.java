package com.sangwoo.possystem.ui.main.admin.customer;

import com.sangwoo.possystem.App;
import com.sangwoo.possystem.common.utils.DateUtils;
import com.sangwoo.possystem.common.widgets.Toast;
import com.sangwoo.possystem.domain.model.Customer;
import com.sangwoo.possystem.ui.BasePanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;

public class CustomerTab extends BasePanel implements CustomerTabContract.View {

    private static final Logger logger = LogManager.getLogger();

    private JLabel customerLabel;
    private JTextField customerTextField;
    private JButton joinButton;
    private JButton inquiryButton;
    private JScrollPane scrollPane;
    private JTextArea viewArea;
    private CustomerRegisterPrompt prompt;

    @Inject
    CustomerTabContract.Presenter presenter;

    @Override
    public void initView() {
        presenter.bindView(this);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        prompt = new CustomerRegisterPrompt();

        customerLabel = new JLabel("고객명");
        customerTextField = new JTextField();
        joinButton = new JButton("가입");
        inquiryButton = new JButton("조회");
        viewArea = new JTextArea();
        scrollPane = new JScrollPane(viewArea);

        viewArea.setEditable(false);

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 0;
        c.anchor = GridBagConstraints.NORTHWEST;
        add(customerLabel, c);

        c.gridy = 1;
        c.ipadx = 60;
        c.weightx = 0;
        c.weighty = 0;
        add(customerTextField, c);

        c.gridx = 1;
        c.ipadx = 0;
        c.weightx = 1;
        c.anchor = GridBagConstraints.NORTHEAST;
        add(joinButton, c);

        c.gridx = 2;
        c.weightx = 0;
        add(inquiryButton, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        add(scrollPane, c);

        initButtonListener();
    }

    @Override
    public void inject() {
        DaggerCustomerTabComponent.builder()
                .appComponent(App.getAppComponent())
                .customerTabPresenterModule(new CustomerTabPresenterModule())
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
    public void showCustomerInfo(final Customer customer) {
        viewArea.setText(getCustomerInfoString(customer));
    }

    @Override
    public void failedInquiry(String message) {
        if (message == null)
            Toast.makeToast(getParentJFrame(), "  조회 실패  ");
        else
            Toast.makeToast(getParentJFrame(), message);
    }

    @Override
    public void succeedRegistration() {
        Toast.makeToast(getParentJFrame(), "  등록 성공  ");
        prompt.hidePrompt();
    }

    @Override
    public void failedRegistration(String message) {
        if (message == null)
            Toast.makeToast(getParentJFrame(), "  가입 실패  ");
        else
            Toast.makeToast(getParentJFrame(), message);
    }

    private void initButtonListener() {
        joinButton.addActionListener(e -> prompt.showPrompt());
        inquiryButton.addActionListener(e -> presenter.inquiryCustomer(customerTextField.getText()));
        prompt.setOnRegisterListener(presenter::registerCustomer);
    }

    private String getCustomerInfoString(final Customer customer) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("고객명:%s\n", customer.getName()));
        sb.append(String.format("고객 ID:%04d\n", customer.getId()));
        sb.append(String.format("생일:%s\n", DateUtils.convertToBirthString(customer.getBirthDate())));
        sb.append(String.format("전화번호:%s\n", customer.getPhoneNum()));
        sb.append(String.format("고객등급:%s\n", customer.getLevel()));
        sb.append(String.format("총 구매금액:%s원\n", customer.getPurchaseAmount()));

        return sb.toString();
    }
}
