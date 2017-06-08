package com.sangwoo.possystem.ui;

public interface BasePresenter<T extends BaseView> {
    void bindView(T view);
}
