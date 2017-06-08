package com.sangwoo.possystem;

import com.sangwoo.possystem.data.database.DatabaseProxyImpl;
import com.sangwoo.possystem.data.executor.JobExecutor;
import com.sangwoo.possystem.data.repository.AppDataSource;
import com.sangwoo.possystem.domain.database.DatabaseProxy;
import com.sangwoo.possystem.domain.executor.PostExecutionThread;
import com.sangwoo.possystem.domain.executor.ThreadExecutor;
import com.sangwoo.possystem.domain.repository.DataSource;
import com.sangwoo.possystem.ui.UIThread;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
class AppModule {

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    DatabaseProxy provideDatabase(DatabaseProxyImpl database) {
        return database;
    }

    @Provides
    @Singleton
    DataSource provideDataSource(AppDataSource appDataSource) {
        return appDataSource;
    }
}
