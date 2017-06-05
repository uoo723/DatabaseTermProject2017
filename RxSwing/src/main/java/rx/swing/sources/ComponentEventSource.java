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
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposables;
import rx.observables.SwingObservable;
import rx.schedulers.SwingScheduler;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import static rx.swing.sources.ComponentEventSource.Predicate.RESIZED;

public enum ComponentEventSource {
    ; // no instances

    /**
     * @see rx.observables.SwingObservable#fromComponentEvents
     */
    public static Observable<ComponentEvent> fromComponentEventsOf(final Component component) {
        return Observable.<ComponentEvent>create(e -> {
            final ComponentListener listener = new ComponentListener() {
                @Override
                public void componentResized(ComponentEvent event) {
                    e.onNext(event);
                }

                @Override
                public void componentMoved(ComponentEvent event) {
                    e.onNext(event);
                }

                @Override
                public void componentShown(ComponentEvent event) {
                    e.onNext(event);
                }

                @Override
                public void componentHidden(ComponentEvent event) {
                    e.onNext(event);
                }
            };
            component.addComponentListener(listener);
            e.setDisposable(Disposables.fromAction(() -> component.removeComponentListener(listener)));
        })
                .subscribeOn(SwingScheduler.getInstance())
                .unsubscribeOn(SwingScheduler.getInstance());
    }

    /**
     * @see SwingObservable#fromResizing
     */
    public static Observable<Dimension> fromResizing(final Component component) {
        return fromComponentEventsOf(component).filter(RESIZED).map(event -> event.getComponent().getSize());
    }

    /**
     * Predicates that help with filtering observables for specific component events.
     */
    public enum Predicate implements io.reactivex.functions.Predicate<ComponentEvent> {
        RESIZED(ComponentEvent.COMPONENT_RESIZED),
        HIDDEN(ComponentEvent.COMPONENT_HIDDEN),
        MOVED(ComponentEvent.COMPONENT_MOVED),
        SHOWN(ComponentEvent.COMPONENT_SHOWN);

        private final int id;

        Predicate(int id) {
            this.id = id;
        }

        @Override
        public boolean test(@NonNull ComponentEvent event) throws Exception {
            return event.getID() == id;
        }
    }
}
