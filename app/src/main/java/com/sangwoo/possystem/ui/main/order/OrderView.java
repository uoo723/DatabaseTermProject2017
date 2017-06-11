package com.sangwoo.possystem.ui.main.order;

import com.sangwoo.possystem.App;
import com.sangwoo.possystem.common.widgets.Toast;
import com.sangwoo.possystem.domain.model.Menu;
import com.sangwoo.possystem.domain.model.Table;
import com.sangwoo.possystem.ui.BasePanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class OrderView extends BasePanel implements OrderContract.View {

    public interface OnTableStateChangedListener {
        void onChanged(int tableNum, boolean order);
    }

    private static final Logger logger = LogManager.getLogger();

    private OnTableStateChangedListener listener;

    private JLabel titleLabel;
    private JPanel contentPanel;

    private JTextArea orderTextArea;
    private JLabel customerLabel;
    private JLabel tableLabel;
    private JTextField customerTextField;
    private JComboBox<String> tableComboBox;
    private JButton orderButton;
    private JButton cancelButton;
    private JButton payButton;
    private JScrollPane scrollPane;

    private final static String[] TABLE_NUMS = new String[20];

    static {
        IntStream.range(0, 20).forEach(i -> TABLE_NUMS[i] = (i + 1) + "");
    }

    @Inject
    OrderContract.Presenter presenter;

    @Override
    public void initView() {
        presenter.bindView(this);

        setLayout(new BorderLayout());

        titleLabel = new JLabel("주문내역");
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());

        Border padding = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        Border lineBorder = BorderFactory.createLineBorder(Color.black);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(lineBorder, padding));

        initContent();
        initButtonListener();
        tableComboBox.setSelectedIndex(0);
        presenter.loadOrderInfo(1);

        add(titleLabel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        presenter.loadTablesInfo();
    }

    @Override
    public void inject() {
        DaggerOrderComponent.builder()
                .appComponent(App.getAppComponent())
                .orderPresenterModule(new OrderPresenterModule())
                .build()
                .inject(this);
    }

    @Override
    public void setTitle(String title) { /* Do nothing */ }

    @Override
    public void showTableState(List<Table> tables) {
        tables.forEach(table -> {
            if (listener != null)
                listener.onChanged(table.getTableNum(), table.isOrdering());
        });
    }

    @Override
    public void succeedOrder(int tableNum) {
        if (listener != null)
            listener.onChanged(tableNum, true);

        Toast.makeToast(getParentJFrame(), "  주문 성공  ");
    }

    @Override
    public void failedOrder() {
        Toast.makeToast(getParentJFrame(), "  주문 실패  ");
    }

    @Override
    public void succeedCancel(int tableNum) {
        if (listener != null)
            listener.onChanged(tableNum, false);

        Toast.makeToast(getParentJFrame(), "  주문 취소  ");
        orderTextArea.setText("");
    }

    @Override
    public void failedCancel() {
        Toast.makeToast(getParentJFrame(), "  주문 취소를 실패했습니다  ");
    }

    @Override
    public void succeedPay(int tableNum) {
        if (listener != null)
            listener.onChanged(tableNum, false);
        Toast.makeToast(getParentJFrame(), "  결제 성공  ");
        customerTextField.setText("");
        orderTextArea.setText("");
    }

    @Override
    public void failedPay(String message) {
        if (message == null)
            Toast.makeToast(getParentJFrame(), "  결제 실패  ");
        else
            Toast.makeToast(getParentJFrame(), message);
    }

    @Override
    public void cancelOrder(int tableNum, String message) {
        if (listener != null) {
            listener.onChanged(tableNum, false);
        }

        Toast.makeToast(getParentJFrame(), message);
    }

    @Override
    public void failedAddMenu() {
        Toast.makeToast(getParentJFrame(), "  메뉴 변경 실패  ");
    }

    public void setOnTableStateChangedListener(OnTableStateChangedListener listener) {
        this.listener = listener;
    }

    @Override
    public void showMenuList(List<Menu> menuList) {
        orderTextArea.setText(getMenuListInfo(menuList));
    }

    @Override
    public void emptyMenu() {
        Toast.makeToast(getParentJFrame(), "  메뉴가 선택되지 않았습니다  ");
    }

    public void addMenu(Menu menu) {
        logger.info("menu: " + menu);
        presenter.addMenu(menu);
    }

    @Override
    public void requiredEmployeeLogin() {
        Toast.makeToast(getParentJFrame(), "  직원 로그인을 해주세요  ");
    }

    private void initContent() {
        orderTextArea = new JTextArea();
        orderTextArea.setEditable(false);

        scrollPane = new JScrollPane(orderTextArea);

        customerLabel = new JLabel("고객명");
        tableLabel = new JLabel("테이블명");
        customerTextField = new JTextField();
        tableComboBox = new JComboBox<>(TABLE_NUMS);
        orderButton = new JButton("주문");
        cancelButton = new JButton("취소");
        payButton = new JButton("결제");

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.9;
        c.weighty = 1;
        c.gridheight = 7;
        c.fill = GridBagConstraints.BOTH;
        contentPanel.add(scrollPane, c);

        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.1;
        c.weighty = 0;
        c.gridheight = 1;
        c.insets = new Insets(0, 10, 0, 0);
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.NORTHWEST;
        contentPanel.add(customerLabel, c);

        c.gridy = 1;
        c.ipadx = 60;
        contentPanel.add(customerTextField, c);

        c.gridy = 2;
        c.ipadx = 0;
        contentPanel.add(tableLabel, c);

        c.gridy = 3;
        c.anchor = GridBagConstraints.NORTHEAST;
        c.insets = new Insets(0, 10, 20, 0);
        contentPanel.add(tableComboBox, c);

        c.gridy = 4;
        c.insets = new Insets(0, 10, 0, 0);
        contentPanel.add(orderButton, c);

        c.gridy = 5;
        contentPanel.add(cancelButton, c);

        c.gridy = 6;
        contentPanel.add(payButton, c);
    }

    private void initButtonListener() {
        tableComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String tableNumStr = (String) e.getItem();
                presenter.loadOrderInfo(Integer.parseInt(tableNumStr));
            }
        });

        orderButton.addActionListener(e -> presenter.order());
        cancelButton.addActionListener(e -> presenter.cancel());
        payButton.addActionListener(e -> presenter.pay(customerTextField.getText()));
    }

    private String getMenuListInfo(List<Menu> menuList) {
        final StringBuilder sb = new StringBuilder();
        final AtomicInteger total = new AtomicInteger(0);
        menuList.forEach(menu -> {
            sb.append(String.format("%s\t%d\n", menu.getName(), menu.getPrice()));
            total.addAndGet(menu.getPrice());
        });

        sb.append("\n\n\n");
        sb.append("---------------------\n");
        sb.append(String.format("총합계\t%d", total.get()));
        return sb.toString();
    }
}
