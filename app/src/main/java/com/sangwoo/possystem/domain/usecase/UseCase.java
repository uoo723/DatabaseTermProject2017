package com.sangwoo.possystem.domain.usecase;

import com.sangwoo.possystem.domain.executor.PostExecutionThread;
import com.sangwoo.possystem.domain.executor.ThreadExecutor;
import dagger.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public abstract class UseCase<Input, Output> {

    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;
    private CompositeDisposable mDisposable = new CompositeDisposable();

    UseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        mThreadExecutor = threadExecutor;
        mPostExecutionThread = postExecutionThread;
    }

    abstract Observable<Output> buildUseCaseObservable(Input input);

    public Disposable execute(Input input) {
        final Disposable disposable = buildUseCaseObservable(input)
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mPostExecutionThread.getScheduler())
                .subscribe();
        addDisposable(disposable);
        return disposable;
    }

    public Disposable execute(Input input, Consumer<? super Output> onNext) {
        Preconditions.checkNotNull(onNext);
        final Disposable disposable = buildUseCaseObservable(input)
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mPostExecutionThread.getScheduler())
                .subscribe(onNext);
        addDisposable(disposable);
        return disposable;
    }

    public Disposable execute(Input input, Consumer<? super Output> onNext,
                              Consumer<? super Throwable> onError) {
        Preconditions.checkNotNull(onNext);
        Preconditions.checkNotNull(onError);
        final Disposable disposable = buildUseCaseObservable(input)
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mPostExecutionThread.getScheduler())
                .subscribe(onNext, onError);
        addDisposable(disposable);
        return disposable;
    }

    public Disposable execute(Input input, Consumer<? super Output> onNext,
                              Consumer<? super Throwable> onError, Action onComplete) {
        Preconditions.checkNotNull(onNext);
        Preconditions.checkNotNull(onError);
        Preconditions.checkNotNull(onComplete);
        final Disposable disposable = buildUseCaseObservable(input)
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mPostExecutionThread.getScheduler())
                .subscribe(onNext, onError, onComplete);
        addDisposable(disposable);
        return disposable;
    }

    public void dispose() {
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    public void clear() {
        if (mDisposable.isDisposed()) {
            mDisposable = new CompositeDisposable();
        }
    }

    private void addDisposable(Disposable disposable) {
        Preconditions.checkNotNull(disposable);
        Preconditions.checkNotNull(mDisposable);
        mDisposable.add(disposable);
    }
}
