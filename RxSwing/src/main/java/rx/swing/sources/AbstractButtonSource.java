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
import io.reactivex.disposables.Disposables;
import rx.schedulers.SwingScheduler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public enum AbstractButtonSource {
    ; // no instances

    /**
     * @see rx.observables.SwingObservable#fromButtonAction
     */
    public static Observable<ActionEvent> fromActionOf(final AbstractButton button) {
        return Observable.<ActionEvent>create(e -> {
            final ActionListener listener = e::onNext;
            button.addActionListener(listener);
            e.setDisposable(Disposables.fromAction(() -> button.removeActionListener(listener)));
        })
                .subscribeOn(SwingScheduler.getInstance())
                .unsubscribeOn(SwingScheduler.getInstance());
    }
}
