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
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposables;
import rx.schedulers.SwingScheduler;

import java.awt.*;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;

public enum ContainerEventSource {
    ; // no instances

    /**
     * @see rx.observables.SwingObservable#fromContainerEvents
     */
    public static Observable<ContainerEvent> fromContainerEventsOf(final Container container) {
        return Observable.<ContainerEvent>create(e -> {
            final ContainerListener listener = new ContainerListener() {
                @Override
                public void componentAdded(ContainerEvent event) {
                    e.onNext(event);
                }

                @Override
                public void componentRemoved(ContainerEvent event) {
                    e.onNext(event);
                }
            };
            container.addContainerListener(listener);
            e.setDisposable(Disposables.fromAction(() -> container.removeContainerListener(listener)));
        })
                .subscribeOn(SwingScheduler.getInstance())
                .observeOn(SwingScheduler.getInstance());
    }

    public enum Predicate implements io.reactivex.functions.Predicate<ContainerEvent> {
        COMPONENT_ADDED(ContainerEvent.COMPONENT_ADDED),
        COMPONENT_REMOVED(ContainerEvent.COMPONENT_REMOVED);

        private final int id;

        Predicate(int id) {
            this.id = id;
        }

        @Override
        public boolean test(@NonNull ContainerEvent event) throws Exception {
            return event.getID() == id;
        }
    }
}
