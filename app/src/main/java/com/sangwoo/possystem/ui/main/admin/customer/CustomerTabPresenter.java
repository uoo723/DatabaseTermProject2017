package com.sangwoo.possystem.ui.main.admin.customer;

import com.sangwoo.possystem.common.utils.DateUtils;
import com.sangwoo.possystem.common.utils.StringUtils;
import com.sangwoo.possystem.domain.model.Customer;
import com.sangwoo.possystem.domain.model.Employee;
import com.sangwoo.possystem.domain.usecase.InquiryCustomer;
import com.sangwoo.possystem.domain.usecase.RegisterCustomer;
import com.sangwoo.possystem.ui.EmployeeLoginSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.sql.BatchUpdateException;
import java.util.Date;
import java.util.NoSuchElementException;

public class CustomerTabPresenter implements CustomerTabContract.Presenter {

    private static final Logger logger = LogManager.getLogger();

    private CustomerTabContract.View view;

    private final InquiryCustomer inquiryCustomerUseCase;
    private final RegisterCustomer registerCustomerUseCase;

    @Inject
    CustomerTabPresenter(InquiryCustomer inquiryCustomerUseCase, RegisterCustomer registerCustomerUseCase) {
        this.inquiryCustomerUseCase = inquiryCustomerUseCase;
        this.registerCustomerUseCase = registerCustomerUseCase;
    }

    @Override
    public void bindView(CustomerTabContract.View view) {
        this.view = view;
    }

    @Override
    public void inquiryCustomer(String name) {
        if (!EmployeeLoginSession.isLogin()) {
            view.requiredEmployeeLogin();
            return;
        }

        if (StringUtils.isEmpty(name)) {
            view.failedInquiry("  이름을 입력하세요  ");
            return;
        }

        inquiryCustomerUseCase.dispose();
        inquiryCustomerUseCase.clear();
        inquiryCustomerUseCase.execute(name.trim(),
                view::showCustomerInfo,
                throwable -> {
                    if (throwable instanceof NoSuchElementException) {
                        view.failedInquiry("  존재하지 않는 고객입니다  ");
                    } else {
                        view.failedInquiry("  알 수 없는 에러  ");
                    }
                });
    }

    @Override
    public void registerClicked() {
        if (!EmployeeLoginSession.isLogin()) {
            view.requiredEmployeeLogin();
            return;
        }

        if (EmployeeLoginSession.getEmployee().getRank() == Employee.Rank.SUPERVISOR) {
            view.registerClickedResult(true);
        } else {
            view.registerClickedResult(false);
        }
    }

    @Override
    public void registerCustomer(String name, String birth, String phoneNum, Customer.Level level) {
        if (!EmployeeLoginSession.isLogin()) {
            view.requiredEmployeeLogin();
            return;
        }

        if (EmployeeLoginSession.getEmployee().getRank() != Employee.Rank.SUPERVISOR) {
            view.failedRegistration("  관리자만 등록할 수 있습니다  ");
            return;
        }

        if (StringUtils.isEmpty(name)) {
            view.failedRegistration("  이름을 입력하세요  ");
            return;
        }

        if (StringUtils.isEmpty(birth)) {
            view.failedInquiry("  생일을 입력하세요  ");
            return;
        }

        Date birthDate;

        try {
            birthDate = DateUtils.convertToBirthDate(birth.trim());
        } catch (Exception e) {
            birthDate = null;
        }

        if (birthDate == null) {
            view.failedRegistration("  생일 형식이 잘못되었습니다  ");
            return;
        }

        if (StringUtils.isEmpty(phoneNum)) {
            view.failedRegistration("  연락처를 입력하세요  ");
            return;
        }

        if (!StringUtils.isDigits(phoneNum) || phoneNum.length() != 4) {
            view.failedRegistration("  연락처 형식이 잘못되었습니다  ");
            return;
        }

        registerCustomerUseCase.dispose();
        registerCustomerUseCase.clear();
        registerCustomerUseCase.execute(createCustomer(name.trim(), birthDate, phoneNum.trim(), level),
                o -> {},
                throwable -> {
                    if (throwable instanceof BatchUpdateException) {
                        BatchUpdateException exception = (BatchUpdateException) throwable;
                        if (exception.getErrorCode() == 1)
                            view.failedRegistration("  이미 존재하는 고객입니다  ");
                        else
                            view.failedRegistration(null);
                    } else {
                        view.failedRegistration(null);
                    }
                },
                view::succeedRegistration);
    }

    private Customer createCustomer(String name, Date birthDate, String phoneNum, Customer.Level level) {
        return new Customer(name, birthDate, phoneNum, level, Customer.Level.getPurchaseAmount(level));
    }
}
