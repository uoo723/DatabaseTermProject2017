package com.sangwoo.possystem.data.executor;

import com.sangwoo.possystem.domain.executor.ThreadExecutor;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Singleton
public class JobExecutor implements ThreadExecutor {

    private final ThreadPoolExecutor mThreadPoolExecutor;

    @Inject
    JobExecutor() {
        mThreadPoolExecutor = new ThreadPoolExecutor(3, 5, 10, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(), new JobThreadFactory());
    }

    @Override
    public void execute(Runnable command) {
        mThreadPoolExecutor.execute(command);
    }

    private static final class JobThreadFactory implements ThreadFactory {
        private int mCounter = 0;

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "possystem_" + mCounter++);
        }
    }
}
