package com.sangwoo.possystem.ui.main.admin.employee;

import com.sangwoo.possystem.common.utils.StringUtils;
import com.sangwoo.possystem.domain.model.Employee;
import com.sangwoo.possystem.domain.usecase.CreateEmployee;
import com.sangwoo.possystem.domain.usecase.InquiryEmployee;
import com.sangwoo.possystem.ui.EmployeeLoginSession;

import javax.inject.Inject;
import java.sql.BatchUpdateException;

public class EmployeeTabPresenter implements EmployeeTabContract.Presenter {

    private EmployeeTabContract.View view;

    private final InquiryEmployee inquiryEmployeeUseCase;
    private final CreateEmployee createEmployeeUseCase;

    @Inject
    EmployeeTabPresenter(InquiryEmployee inquiryEmployeeUseCase, CreateEmployee createEmployeeUseCase) {
        this.inquiryEmployeeUseCase = inquiryEmployeeUseCase;
        this.createEmployeeUseCase = createEmployeeUseCase;
    }

    @Override
    public void bindView(EmployeeTabContract.View view) {
        this.view = view;
    }

    @Override
    public void inquiry(String name) {
        if (!EmployeeLoginSession.isLogin()) {
            view.requiredEmployeeLogin();
            return;
        }

        if (StringUtils.isEmpty(name)) {
            view.failedInquiry("  이름을 입력하세요  ");
            return;
        }

        inquiryEmployeeUseCase.dispose();
        inquiryEmployeeUseCase.clear();
        inquiryEmployeeUseCase.execute(name.trim(), view::showEmployeeInfo,
                throwable -> view.failedInquiry(null));
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
    public void register(String name, Employee.Rank rank) {
        if (!EmployeeLoginSession.isLogin()) {
            view.requiredEmployeeLogin();
            return;
        }

        if (StringUtils.isEmpty(name)) {
            view.failedRegistration("  이름을 입력하세요  ");
            return;
        }

        final String employeeId = StringUtils.generateFourNumString();
        Employee employee = new Employee(name.trim(), employeeId, rank);

        createEmployeeUseCase.dispose();
        createEmployeeUseCase.clear();
        createEmployeeUseCase.execute(employee, o -> {},
                throwable -> {
                    if (throwable instanceof BatchUpdateException) {
                        BatchUpdateException exception = (BatchUpdateException) throwable;
                        if (exception.getErrorCode() == 1)
                            view.failedRegistration("  이미 존재하는 직원입니다  ");
                        else
                            view.failedRegistration(null);
                    } else {
                        view.failedRegistration(null);
                    }
                }, () -> view.succeedRegistration(
                        String.format("  등록성공 사원 아이디는 %s 입니다  ", employeeId)));
    }
}
