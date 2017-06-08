package com.sangwoo.possystem;

import com.sangwoo.possystem.domain.database.DatabaseProxy;
import com.sangwoo.possystem.domain.executor.PostExecutionThread;
import com.sangwoo.possystem.domain.executor.ThreadExecutor;
import com.sangwoo.possystem.domain.repository.DataSource;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    ThreadExecutor getThreadExecutor();

    PostExecutionThread getPostExecutionThread();

    DatabaseProxy getDatabase();

    DataSource getDataSource();
}
