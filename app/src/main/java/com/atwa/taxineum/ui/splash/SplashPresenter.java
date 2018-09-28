package com.atwa.taxineum.ui.splash;

import com.atwa.taxineum.data.DataManager;
import com.atwa.taxineum.ui.base.BasePresenter;
import com.atwa.taxineum.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Ahmed Atwa on 3/31/2018.
 */

public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V>
        implements SplashMvpPresenter<V> {

   @Inject
   public SplashPresenter(DataManager dataManager,
                          SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable) {
      super(dataManager, schedulerProvider, compositeDisposable);
   }

   @Override
   public void onAttach(V mvpView) {
      super.onAttach(mvpView);
      if (!isViewAttached())
         return;
      decideNextActivity();
   }

   private void decideNextActivity() {
      if (getDataManager().getCurrentUserLoggedInMode()
              == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType()) {
         getMvpView().openLoginActivity();
      } else {
         getMvpView().openMainActivity();
      }
   }

}
