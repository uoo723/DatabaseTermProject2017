package com.sangwoo.possystem.ui.main.order;

import com.sangwoo.possystem.common.utils.StringUtils;
import com.sangwoo.possystem.domain.model.Menu;
import com.sangwoo.possystem.domain.model.Order;
import com.sangwoo.possystem.domain.model.Payment;
import com.sangwoo.possystem.domain.usecase.*;
import com.sangwoo.possystem.ui.EmployeeLoginSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderPresenter implements OrderContract.Presenter {

    private static final Logger logger = LogManager.getLogger();

    private OrderContract.View view;
    private List<Menu> menuList = new ArrayList<>();

    private final RequestOrder requestOrderUseCase;
    private final InquiryOrder inquiryOrderUseCase;
    private final CancelOrder cancelOrderUseCase;
    private final RequestPayment requestPaymentUseCase;
    private final InquiryCustomer inquiryCustomerUseCase;
    private final GetTables getTablesUseCase;
    private Order order;

    @Inject
    OrderPresenter(RequestOrder requestOrder, InquiryOrder inquiryOrder, CancelOrder cancelOrder,
                   RequestPayment requestPayment, InquiryCustomer inquiryCustomer, GetTables getTables) {
        requestOrderUseCase = requestOrder;
        inquiryOrderUseCase = inquiryOrder;
        cancelOrderUseCase = cancelOrder;
        requestPaymentUseCase = requestPayment;
        inquiryCustomerUseCase = inquiryCustomer;
        getTablesUseCase = getTables;
    }

    @Override
    public void bindView(OrderContract.View view) {
        this.view = view;
    }

    @Override
    public void loadTablesInfo() {
        getTablesUseCase.execute(null, view::showTableState);
    }

    @Override
    public void order() {
        if (!EmployeeLoginSession.isLogin()) {
            view.requiredEmployeeLogin();
            return;
        }

        if (menuList.isEmpty()) {
            view.emptyMenu();
            return;
        }

        requestOrderUseCase.dispose();
        requestOrderUseCase.clear();

        requestOrderUseCase.execute(order, o -> {},
                throwable -> view.failedOrder(),
                () -> {
                    order.getTable().setOrdering(true);
                    order.setMenus(menuList);
                    view.succeedOrder(order.getTable().getTableNum());
                });
    }

    @Override
    public void cancel() {
        if (!EmployeeLoginSession.isLogin()) {
            view.requiredEmployeeLogin();
            return;
        }

        cancelOrderUseCase.dispose();
        cancelOrderUseCase.clear();
        cancelOrderUseCase.execute(order, o -> {},
                throwable -> view.failedCancel(),
                () -> {
                    order.getTable().setOrdering(false);
                    menuList.clear();
                    view.succeedCancel(order.getTable().getTableNum());
                });
    }

    @Override
    public void pay(String customerName) {
        if (!EmployeeLoginSession.isLogin()) {
            view.requiredEmployeeLogin();
            return;
        }

        if (menuList.isEmpty()) {
            view.failedPay("  메뉴를 선택해주세요  ");
            return;
        }

        if (!order.getTable().isOrdering()) {
            view.failedPay("  주문을 먼저 해주세요  ");
            return;
        }

        if (StringUtils.isEmpty(customerName) || "비회원".equals(customerName)) {
            Payment payment = new Payment(order, null, EmployeeLoginSession.getEmployee());
            internalPay(payment);
        } else {
            inquiryCustomerUseCase.execute(customerName.trim(), customer -> {
                Payment payment = new Payment(order, customer, EmployeeLoginSession.getEmployee());
                internalPay(payment);
            }, throwable -> {
                if (throwable instanceof NoSuchElementException) {
                    view.failedPay("  존재하지않는 고객입니다  ");
                } else {
                    view.failedPay("  알 수 없는 에러입니다  ");
                }
            });
        }
    }

    @Override
    public void loadOrderInfo(int tableNum) {
        inquiryOrderUseCase.dispose();
        inquiryOrderUseCase.clear();

        // tableNum == tableId
        inquiryOrderUseCase.execute(tableNum, order -> {
            logger.info("table id: " + order.getTable().getId());
            logger.info("# of menus: " + order.getMenus().size());
            this.order = order;
            menuList = order.getMenus();

            view.showMenuList(menuList);
        });
    }

    @Override
    public void addMenu(Menu menu) {
        if (menuList.contains(menu)) {
            menuList.remove(menu);
        } else {
            menuList.add(menu);
        }

        if (order.getTable().isOrdering()) {
            cancelOrderUseCase.dispose();
            cancelOrderUseCase.clear();
            cancelOrderUseCase.execute(order, o -> {},
                    throwable -> view.failedAddMenu(),
                    () -> {
                        order.getTable().setOrdering(false);
                        view.cancelOrder(order.getTable().getTableNum(), "  메뉴 변경으로 주문이 취소되었습니다  ");
                        view.showMenuList(menuList);
                    });
        } else {
            view.showMenuList(menuList);
        }
    }

    private void internalPay(Payment payment) {
        requestPaymentUseCase.dispose();
        requestPaymentUseCase.clear();
        requestPaymentUseCase.execute(payment, o -> {},
                throwable -> {
                    throwable.printStackTrace();
                    view.failedPay("  결제를 실패했습니다  ");
                },
                () -> {
                    order.getMenus().clear();
                    view.succeedPay(order.getTable().getTableNum());
                });
    }
}
