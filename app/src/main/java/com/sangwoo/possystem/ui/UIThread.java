package com.sangwoo.possystem.ui;

import com.sangwoo.possystem.domain.executor.PostExecutionThread;
import io.reactivex.Scheduler;
import rx.schedulers.SwingScheduler;

import javax.inject.Inject;

public class UIThread implements PostExecutionThread {

    @Inject
    UIThread() {}

    @Override
    public Scheduler getScheduler() {
        return SwingScheduler.getInstance();
    }
}
