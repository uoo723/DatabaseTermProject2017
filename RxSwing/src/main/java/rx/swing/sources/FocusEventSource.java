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
package rx.swing.sources;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposables;
import rx.schedulers.SwingScheduler;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public enum FocusEventSource {
    ; // no instances

    /**
     * @see rx.observables.SwingObservable#fromFocusEvents
     */
    public static Observable<FocusEvent> fromFocusEventsOf(final Component component) {
        return Observable.<FocusEvent>create(e -> {
            final FocusListener listener = new FocusListener() {
                @Override
                public void focusGained(FocusEvent event) {
                    e.onNext(event);
                }

                @Override
                public void focusLost(FocusEvent event) {
                    e.onNext(event);
                }
            };
            component.addFocusListener(listener);
            e.setDisposable(Disposables.fromAction(() -> component.removeFocusListener(listener)));
        })
                .subscribeOn(SwingScheduler.getInstance())
                .unsubscribeOn(SwingScheduler.getInstance());
    }

    /**
     * Predicates that help with filtering observables for specific focus events.
     */
    public enum Predicate implements io.reactivex.functions.Predicate<FocusEvent> {
        FOCUS_GAINED(FocusEvent.FOCUS_GAINED),
        FOCUS_LOST(FocusEvent.FOCUS_LOST);

        private final int id;

        Predicate(int id) {
            this.id = id;
        }

        @Override
        public boolean test(@NonNull FocusEvent event) throws Exception {
            return event.getID() == id;
        }
    }
}
