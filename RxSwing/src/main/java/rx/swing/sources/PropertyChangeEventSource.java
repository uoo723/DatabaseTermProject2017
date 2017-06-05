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

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public enum PropertyChangeEventSource {
    ; // no instances

    public static Observable<PropertyChangeEvent> fromPropertyChangeEventsOf(final Component component) {
        return Observable.<PropertyChangeEvent>create(e -> {
            final PropertyChangeListener listener = e::onNext;
            component.addPropertyChangeListener(listener);
            e.setDisposable(Disposables.fromAction(() -> component.removePropertyChangeListener(listener)));
        })
                .subscribeOn(SwingScheduler.getInstance())
                .unsubscribeOn(SwingScheduler.getInstance());
    }
}
