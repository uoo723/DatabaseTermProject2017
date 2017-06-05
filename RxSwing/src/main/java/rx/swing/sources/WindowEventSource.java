/**
 * Copyright 2015 Netflix
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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public enum WindowEventSource {
    ; // no instances

    /**
     * @see rx.observables.SwingObservable#fromWindowEventsOf(Window)
     */
    public static Observable<WindowEvent> fromWindowEventsOf(final Window window) {
        return Observable.<WindowEvent>create(e -> {
            final WindowListener listener = new WindowListener() {
                @Override
                public void windowOpened(WindowEvent event) {
                    e.onNext(event);
                }

                @Override
                public void windowClosing(WindowEvent event) {
                    e.onNext(event);
                }

                @Override
                public void windowClosed(WindowEvent event) {
                    e.onNext(event);
                }

                @Override
                public void windowIconified(WindowEvent event) {
                    e.onNext(event);
                }

                @Override
                public void windowDeiconified(WindowEvent event) {
                    e.onNext(event);
                }

                @Override
                public void windowActivated(WindowEvent event) {
                    e.onNext(event);
                }

                @Override
                public void windowDeactivated(WindowEvent event) {
                    e.onNext(event);
                }
            };
            window.addWindowListener(listener);
            e.setDisposable(Disposables.fromAction(() -> window.removeWindowListener(listener)));
        })
                .subscribeOn(SwingScheduler.getInstance())
                .unsubscribeOn(SwingScheduler.getInstance());
    }
}
