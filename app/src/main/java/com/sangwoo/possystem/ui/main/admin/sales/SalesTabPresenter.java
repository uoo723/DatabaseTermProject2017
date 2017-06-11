package com.sangwoo.possystem.ui.main.admin.sales;

import com.sangwoo.possystem.domain.model.Employee;
import com.sangwoo.possystem.domain.usecase.InquirySales;
import com.sangwoo.possystem.ui.EmployeeLoginSession;

import javax.inject.Inject;
import java.util.Date;

public class SalesTabPresenter implements SalesTabContract.Presenter {

    private SalesTabContract.View view;

    private final InquirySales inquirySalesUseCase;

    @Inject
    SalesTabPresenter(InquirySales inquirySalesUseCase) {
        this.inquirySalesUseCase = inquirySalesUseCase;
    }

    @Override
    public void bindView(SalesTabContract.View view) {
        this.view = view;
    }

    @Override
    public void inquiry(Date date) {
        if (!EmployeeLoginSession.isLogin()) {
            view.requiredEmployeeLogin();
            return;
        }

        if (EmployeeLoginSession.getEmployee().getRank() != Employee.Rank.SUPERVISOR) {
            view.failedInquiry("  관리자만 조회할 수 있습니다  ");
            return;
        }

        inquirySalesUseCase.execute(date, view::showSales,
                throwable -> {
                    throwable.printStackTrace();
                    view.failedInquiry(null);
                });
    }
}
