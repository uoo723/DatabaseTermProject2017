/**
 * Copyright 2015 Netflix, Inc.
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
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

public enum HierarchyEventSource {
    ; // no instances

    /**
     * @see rx.observables.SwingObservable#fromHierachyEvents
     */
    public static Observable<HierarchyEvent> fromHierarchyEventsOf(final Component component) {
        return Observable.<HierarchyEvent>create(e -> {
            final HierarchyListener listener = e::onNext;
            component.addHierarchyListener(listener);
            e.setDisposable(Disposables.fromAction(() -> component.removeHierarchyListener(listener)));
        })
                .subscribeOn(SwingScheduler.getInstance())
                .unsubscribeOn(SwingScheduler.getInstance());
    }

    /**
     * @see rx.observables.SwingObservable#fromHierachyBoundsEvents
     */
    public static Observable<HierarchyEvent> fromHierarchyBoundsEventsOf(final Component component) {
        return Observable.<HierarchyEvent>create(e -> {
            final HierarchyBoundsListener listener = new HierarchyBoundsListener() {
                @Override
                public void ancestorMoved(HierarchyEvent event) {
                    e.onNext(event);
                }

                @Override
                public void ancestorResized(HierarchyEvent event) {
                    e.onNext(event);
                }
            };
            component.addHierarchyBoundsListener(listener);
            e.setDisposable(Disposables.fromAction(() -> component.removeHierarchyBoundsListener(listener)));
        })
                .subscribeOn(SwingScheduler.getInstance())
                .unsubscribeOn(SwingScheduler.getInstance());
    }

    public enum Predicate implements io.reactivex.functions.Predicate<HierarchyEvent> {
        ANCESTOR_RESIZED(HierarchyEvent.ANCESTOR_RESIZED),
        ANCESTOR_MOVED(HierarchyEvent.ANCESTOR_MOVED);

        private final int id;

        Predicate(int id) {
            this.id = id;
        }

        @Override
        public boolean test(@NonNull HierarchyEvent event) throws Exception {
            return event.getID() == id;
        }
    }
}
