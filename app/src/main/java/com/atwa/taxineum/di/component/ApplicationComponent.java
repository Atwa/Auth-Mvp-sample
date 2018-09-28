package com.atwa.taxineum.di.component;

import android.app.Application;
import android.content.Context;

import com.atwa.taxineum.MvpApp;
import com.atwa.taxineum.data.DataManager;
import com.atwa.taxineum.di.ApplicationContext;
import com.atwa.taxineum.di.module.ApplicationModule;
import com.atwa.taxineum.service.SyncService;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MvpApp app);

    void inject(SyncService service);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}