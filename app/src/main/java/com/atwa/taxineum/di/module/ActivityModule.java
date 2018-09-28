package com.atwa.taxineum.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.atwa.taxineum.di.ActivityContext;
import com.atwa.taxineum.di.PerActivity;
import com.atwa.taxineum.ui.login.LoginMvpPresenter;
import com.atwa.taxineum.ui.login.LoginMvpView;
import com.atwa.taxineum.ui.login.LoginPresenter;
import com.atwa.taxineum.ui.main.MainMvpPresenter;
import com.atwa.taxineum.ui.main.MainMvpView;
import com.atwa.taxineum.ui.main.MainPresenter;
import com.atwa.taxineum.ui.register.RegisterMvpPresenter;
import com.atwa.taxineum.ui.register.RegisterMvpView;
import com.atwa.taxineum.ui.register.RegisterPresenter;
import com.atwa.taxineum.ui.splash.SplashMvpPresenter;
import com.atwa.taxineum.ui.splash.SplashMvpView;
import com.atwa.taxineum.ui.splash.SplashPresenter;
import com.atwa.taxineum.utils.rx.AppSchedulerProvider;
import com.atwa.taxineum.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(
            SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LoginMvpPresenter<LoginMvpView> provideLoginPresenter(
            LoginPresenter<LoginMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    RegisterMvpPresenter<RegisterMvpView> provideRegisterPresenter(
            RegisterPresenter<RegisterMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainPresenter(
            MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
