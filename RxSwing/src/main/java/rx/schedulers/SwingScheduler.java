/**
 * Copyright 2014 Netflix, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rx.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.internal.subscriptions.BooleanSubscription;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

/**
 * Executes work on the Swing UI thread.
 * This scheduler should only be used with actions that execute quickly.
 *
 * If the calling thread is the Swing UI thread, and no delay parameter is
 * provided, the action will run immediately. Otherwise, if the calling
 * thread is NOT the Swing UI thread, the action will be deferred until
 * all pending UI events have been processed.
 */
public final class SwingScheduler extends Scheduler {
    private static final SwingScheduler INSTANCE = new SwingScheduler();

    public static SwingScheduler getInstance() {
        return INSTANCE;
    }

    /* package for unit test */SwingScheduler() {
    }

    @Override
    public Worker createWorker() {
        return new InnerSwingScheduler();
    }

    private static class InnerSwingScheduler extends Worker {

        private final CompositeDisposable innerSubscription = new CompositeDisposable();

        @Override
        public Disposable schedule(@NonNull Runnable run, long delayTime, @NonNull TimeUnit unit) {
            long delay = Math.max(0, unit.toMillis(delayTime));
            assertThatTheDelayIsValidForTheSwingTimer(delay);
            final BooleanSubscription s = new BooleanSubscription();
            final Disposable d = Disposables.fromSubscription(s);

            class ExecuteOnceAction implements ActionListener {
                private Timer timer;

                private void setTimer(Timer timer) {
                    this.timer = timer;
                }

                @Override
                public void actionPerformed(ActionEvent e) {
                    timer.stop();
                    if (innerSubscription.isDisposed() || d.isDisposed())
                        return;
                    run.run();
                    innerSubscription.remove(d);
                }
            }

            ExecuteOnceAction executeOnce = new ExecuteOnceAction();
            final Timer timer = new Timer((int) delay, executeOnce);
            executeOnce.setTimer(timer);
            timer.start();

            innerSubscription.add(d);

            return Disposables.fromAction(() -> {
                timer.stop();
                d.dispose();
                innerSubscription.remove(d);
            });
        }

        @Override
        public void dispose() {
            innerSubscription.dispose();
        }

        @Override
        public boolean isDisposed() {
            return innerSubscription.isDisposed();
        }
    }

    private static void assertThatTheDelayIsValidForTheSwingTimer(long delay) {
        if (delay < 0 || delay > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(String.format("The swing timer only accepts non-negative delays up to %d milliseconds.", Integer.MAX_VALUE));
        }
    }
}
