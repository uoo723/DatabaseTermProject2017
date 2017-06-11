package com.sangwoo.possystem.ui.main.admin.menu;

import com.sangwoo.possystem.common.utils.StringUtils;
import com.sangwoo.possystem.domain.model.Employee;
import com.sangwoo.possystem.domain.model.Menu;
import com.sangwoo.possystem.domain.usecase.CreateMenu;
import com.sangwoo.possystem.domain.usecase.InquiryMenu;
import com.sangwoo.possystem.ui.EmployeeLoginSession;

import javax.inject.Inject;
import java.sql.BatchUpdateException;
import java.util.NoSuchElementException;

public class MenuTabPresenter implements MenuTabContract.Presenter {

    private MenuTabContract.View view;

    private final InquiryMenu inquiryMenuUseCase;
    private final CreateMenu createMenuUseCase;

    @Inject
    MenuTabPresenter(InquiryMenu inquiryMenuUseCase, CreateMenu createMenu) {
        this.inquiryMenuUseCase = inquiryMenuUseCase;
        this.createMenuUseCase = createMenu;
    }

    @Override
    public void bindView(MenuTabContract.View view) {
        this.view = view;
    }

    @Override
    public void inquiry(String name) {
        if (!EmployeeLoginSession.isLogin()) {
            view.requiredEmployeeLoin();
            return;
        }

        if (StringUtils.isEmpty(name)) {
            view.failedInquiry("  메뉴를 입력하세요  ");
            return;
        }

        inquiryMenuUseCase.dispose();
        inquiryMenuUseCase.clear();
        inquiryMenuUseCase.execute(name.trim(), view::showMenuInfo,
                throwable -> {
                    if (throwable instanceof NoSuchElementException)
                        view.failedInquiry("  존재하지않는 메뉴입니다  ");
                    else
                        view.failedInquiry(null);
                });
    }

    @Override
    public void registerClicked() {
        if (!EmployeeLoginSession.isLogin()) {
            view.requiredEmployeeLoin();
            return;
        }

        if (EmployeeLoginSession.getEmployee().getRank() == Employee.Rank.SUPERVISOR) {
            view.registerClickedResult(true);
        } else {
            view.registerClickedResult(false);
        }
    }

    @Override
    public void register(String menuName, String price) {
        if (!EmployeeLoginSession.isLogin()) {
            view.requiredEmployeeLoin();
            return;
        }

        if (EmployeeLoginSession.getEmployee().getRank() != Employee.Rank.SUPERVISOR) {
            view.failedRegistration("  관리자 권한 필요  ");
            return;
        }

        if (StringUtils.isEmpty(menuName)) {
            view.failedRegistration("  메뉴명을 입력하세요  ");
            return;
        }

        if (StringUtils.isEmpty(price)) {
            view.failedInquiry("  가격을 입력하세요  ");
            return;
        }

        if (!StringUtils.isDigits(price.trim())) {
            view.failedRegistration("  가격 형식이 잘못되었습니다  ");
            return;
        }

        Menu menu = new Menu(menuName.trim(), Integer.parseInt(price.trim()));

        createMenuUseCase.dispose();
        createMenuUseCase.clear();
        createMenuUseCase.execute(menu, o -> {},
                throwable -> {
                    if (throwable instanceof BatchUpdateException) {
                        BatchUpdateException exception = (BatchUpdateException) throwable;
                        if (exception.getErrorCode() == 1)
                            view.failedRegistration("  이미 존재하는 메뉴입니다  ");
                        else
                            view.failedRegistration(null);
                    } else {
                        view.failedRegistration(null);
                    }
                }, view::succeedRegistration);
    }
}
