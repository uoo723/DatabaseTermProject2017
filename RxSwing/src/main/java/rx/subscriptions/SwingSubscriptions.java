/**
 * Copyright 2014 Netflix, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rx.subscriptions;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Action;
import rx.schedulers.SwingScheduler;

import javax.swing.*;

public final class SwingSubscriptions {

    private SwingSubscriptions() {
        // no instance
    }

    /**
     * @deprecated All RxSwing sources now use subscribeOn to subscribe and unsubscribe on
     * the Swing thread.
     *
     * Create an Subscription that always runs <code>unsubscribe</code> in the event dispatch thread.
     *
     * @param unsubscribe
     * @return an Subscription that always runs <code>unsubscribe</code> in the event dispatch thread.
     */
    @Deprecated
    public static Disposable unsubscribeInEventDispatchThread(final Action unsubscribe) {
        return Disposables.fromAction(() -> {
            if (SwingUtilities.isEventDispatchThread()) {
                unsubscribe.run();
            } else {
                final Scheduler.Worker inner = SwingScheduler.getInstance().createWorker();
                inner.schedule(() -> {
                    try {
                        unsubscribe.run();
                        inner.dispose();
                    } catch (Exception ignored) {
                    }
                });
            }
        });
    }
}
