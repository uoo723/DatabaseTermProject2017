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
import io.reactivex.disposables.Disposables;
import rx.schedulers.SwingScheduler;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public enum ListSelectionEventSource {
    ; // no instances

    /**
     * @see rx.observables.SwingObservable#fromListSelectionEvents(ListSelectionModel)
     */
    public static Observable<ListSelectionEvent> fromListSelectionEventsOf(final ListSelectionModel listSelectionModel) {
        return Observable.<ListSelectionEvent>create(e -> {
            final ListSelectionListener listener = e::onNext;
            listSelectionModel.addListSelectionListener(listener);
            e.setDisposable(Disposables.fromAction(() -> listSelectionModel.removeListSelectionListener(listener)));
        })
                .subscribeOn(SwingScheduler.getInstance())
                .unsubscribeOn(SwingScheduler.getInstance());
    }
}
