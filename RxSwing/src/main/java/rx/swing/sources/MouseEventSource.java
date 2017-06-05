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
import java.awt.event.*;

public enum MouseEventSource {
    ; // no instances

    /**
     * @see rx.observables.SwingObservable#fromMouseEvents
     */
    public static Observable<MouseEvent> fromMouseEventsOf(final Component component) {
        return Observable.<MouseEvent>create(e -> {
            final MouseListener listener = new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent event) {
                    e.onNext(event);
                }

                @Override
                public void mousePressed(MouseEvent event) {
                    e.onNext(event);
                }

                @Override
                public void mouseReleased(MouseEvent event) {
                    e.onNext(event);
                }

                @Override
                public void mouseEntered(MouseEvent event) {
                    e.onNext(event);
                }

                @Override
                public void mouseExited(MouseEvent event) {
                    e.onNext(event);
                }
            };
            component.addMouseListener(listener);
            e.setDisposable(Disposables.fromAction(() -> component.removeMouseListener(listener)));
        })
                .subscribeOn(SwingScheduler.getInstance())
                .unsubscribeOn(SwingScheduler.getInstance());
    }

    /**
     * @see rx.observables.SwingObservable#fromMouseMotionEvents
     */
    public static Observable<MouseEvent> fromMouseMotionEventsOf(final Component component) {
        return Observable.<MouseEvent>create(e -> {
            final MouseMotionListener listener = new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent event) {
                    e.onNext(event);
                }

                @Override
                public void mouseMoved(MouseEvent event) {
                    e.onNext(event);
                }
            };
            component.addMouseMotionListener(listener);
            e.setDisposable(Disposables.fromAction(() -> component.removeMouseMotionListener(listener)));
        })
                .subscribeOn(SwingScheduler.getInstance())
                .unsubscribeOn(SwingScheduler.getInstance());
    }

    public static Observable<MouseWheelEvent> fromMouseWheelEvents(final Component component) {
        return Observable.<MouseWheelEvent>create(e -> {
            final MouseWheelListener listener = e::onNext;
            component.addMouseWheelListener(listener);
            e.setDisposable(Disposables.fromAction(() -> component.removeMouseWheelListener(listener)));
        })
                .subscribeOn(SwingScheduler.getInstance())
                .unsubscribeOn(SwingScheduler.getInstance());
    }

    /**
     * @see rx.observables.SwingObservable#fromRelativeMouseMotion
     */
    public static Observable<Point> fromRelativeMouseMotion(final Component component) {
        final Observable<MouseEvent> events = fromMouseMotionEventsOf(component);
        return Observable.zip(events, events.skip(1), (ev1, ev2) ->
                new Point(ev2.getX() - ev1.getX(),
                        ev2.getY() - ev1.getY()))
                .subscribeOn(SwingScheduler.getInstance())
                .unsubscribeOn(SwingScheduler.getInstance());
    }

}
