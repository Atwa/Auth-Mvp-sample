package com.atwa.taxineum.di.component;

import com.atwa.taxineum.di.PerActivity;
import com.atwa.taxineum.di.module.ActivityModule;
import com.atwa.taxineum.ui.login.LoginActivity;
import com.atwa.taxineum.ui.main.MainActivity;
import com.atwa.taxineum.ui.register.RegisterActivity;
import com.atwa.taxineum.ui.splash.SplashActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SplashActivity activity);

    void inject(LoginActivity activity);

    void inject(RegisterActivity activity);

    void inject(MainActivity activity);

}
