package com.sangwoo.possystem.ui.main.admin.sales;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.sangwoo.possystem.App;
import com.sangwoo.possystem.common.widgets.Toast;
import com.sangwoo.possystem.domain.model.Sales;
import com.sangwoo.possystem.ui.BasePanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;

public class SalesTab extends BasePanel implements SalesTabContract.View {

    private static final Logger logger = LogManager.getLogger();

    private JLabel termLabel;
    private DatePicker datePicker;
    private JTextArea viewArea;
    private JScrollPane scrollPane;

    @Inject
    SalesTabContract.Presenter presenter;

    @Override
    public void initView() {
        presenter.bindView(this);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        termLabel = new JLabel("기간");
        viewArea = new JTextArea();
        scrollPane = new JScrollPane(viewArea);

        viewArea.setEditable(false);

        DatePickerSettings settings = new DatePickerSettings();
        settings.setAllowKeyboardEditing(false);

        datePicker = new DatePicker(settings);

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
        add(datePicker, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.weighty = 1;
        c.insets = new Insets(10, 0, 0, 0);
        c.fill = GridBagConstraints.BOTH;
        add(scrollPane, c);

        initButtonListener();
    }

    @Override
    public void inject() {
        DaggerSalesTabComponent.builder()
                .appComponent(App.getAppComponent())
                .salesTabPresenterModule(new SalesTabPresenterModule())
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
    public void failedInquiry(String message) {
        if (message == null)
            Toast.makeToast(getParentJFrame(), "  조회 실패  ");
        else
            Toast.makeToast(getParentJFrame(), message);
    }

    @Override
    public void showSales(final Sales sales) {
        viewArea.setText(getSalesInfoString(sales));
    }

    private void initButtonListener() {
        datePicker.addDateChangeListener(event -> {
            LocalDate localDate = event.getNewDate();
            if (localDate == null)
                return;
            Date date = java.sql.Date.valueOf(localDate);
            logger.info("date: " + date);
            presenter.inquiry(date);
        });
    }

    private String getSalesInfoString(final Sales sales) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("일 매출 : %d\n", sales.getTodaySales()));
        sb.append("--------------------------\n");
        sb.append("가장 많이 팔린 메뉴\n");
        sb.append(String.format(":%s\n\n", sales.getMostSoldMenu().getName()));
        sb.append("가장 적게 팔린 메뉴\n");
        sb.append(String.format(":%s\n", sales.getLeastSoldMenu().getName()));
        sb.append("--------------------------\n");
        sb.append(String.format("누적 매출 : %d", sales.getAccumulatedSales()));

        return sb.toString();
    }
}
