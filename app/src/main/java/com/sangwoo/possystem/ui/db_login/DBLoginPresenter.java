package com.sangwoo.possystem.ui.db_login;

import com.sangwoo.possystem.domain.usecase.DBLogin;
import com.sangwoo.possystem.domain.usecase.InitDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class DBLoginPresenter implements DBLoginContract.Presenter {
    private static final Logger logger = LogManager.getLogger();

    private DBLoginContract.View view;

    private final DBLogin dbLoginUseCase;
    private final InitDB initDBUseCase;

    @Inject
    DBLoginPresenter(DBLogin dbLoginUseCase, InitDB initDBUseCase) {
        this.dbLoginUseCase = dbLoginUseCase;
        this.initDBUseCase = initDBUseCase;
    }

    @Override
    public void bindView(DBLoginContract.View view) {
        this.view = view;
        view.setTitle("DB Login");
    }

    @Override
    public void login(String host, String user, String password) {
        DBLogin.Params params = new DBLogin.Params(host, user, password);

        dbLoginUseCase.execute(params,
                o -> {},
                throwable -> view.loginResult(false),
                this::init);
    }

    private void init() {
        initDBUseCase.execute(null,
                o -> {},
                Throwable::printStackTrace,
                () -> {
                    logger.info("init");
                    view.loginResult(true);
                });
    }
}
