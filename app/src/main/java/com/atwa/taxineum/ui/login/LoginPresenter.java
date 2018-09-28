package com.atwa.taxineum.ui.login;

import com.androidnetworking.error.ANError;
import com.atwa.taxineum.R;
import com.atwa.taxineum.data.DataManager;
import com.atwa.taxineum.data.network.model.LoginRequest;
import com.atwa.taxineum.data.network.model.LoginResponse;
import com.atwa.taxineum.ui.base.BasePresenter;
import com.atwa.taxineum.utils.CommonUtils;
import com.atwa.taxineum.utils.Validation;
import com.atwa.taxineum.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Ahmed Atwa on 4/2/2018.
 */

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V> implements LoginMvpPresenter<V> {

   private static final String TAG = "LoginPresenter";

   @Inject
   public LoginPresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
      super(dataManager, schedulerProvider, compositeDisposable);
   }

   @Override
   public void onServerLogin(String email, String password) {
      //validate email and password
      if (email == null || email.isEmpty()) {
         getMvpView().onError(R.string.empty_email);
         return;
      }
      if (!Validation.isEmailValid(email)) {
         getMvpView().onError(R.string.invalid_email);
         return;
      }
      if (password == null || password.isEmpty()) {
         getMvpView().onError(R.string.empty_password);
         return;
      }
      getMvpView().showLoading();

      getCompositeDisposable().add(getDataManager()
              .doServerLoginApiCall(new LoginRequest.ServerLoginRequest(email, password))
              .subscribeOn(getSchedulerProvider().io())
              .observeOn(getSchedulerProvider().ui())
              .subscribe(new Consumer<LoginResponse>() {
                 @Override
                 public void accept(LoginResponse response) throws Exception {
                    getDataManager().updateUserInfo(
                            response.getAccessToken(),
                            response.getUserId(),
                            DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER,
                            response.getUserName(),
                            response.getUserEmail(),
                            response.getGoogleProfilePicUrl());

                    if (!isViewAttached()) {
                       return;
                    }

                    getMvpView().hideLoading();
                    getMvpView().openMainActivity();

                 }
              }, new Consumer<Throwable>() {
                 @Override
                 public void accept(Throwable throwable) throws Exception {

                    if (!isViewAttached()) {
                       return;
                    }

                    getMvpView().hideLoading();

                    // handle the login error here
                    if (throwable instanceof ANError) {
                       ANError anError = (ANError) throwable;
                       handleApiError(anError);
                    }
                 }
              }));

   }

   @Override
   public void onFacebookLogin(String facebookId) {
      //validate email and password
      if (facebookId == null || facebookId.isEmpty()) {
         getMvpView().onError(R.string.empty_password);
         return;
      }
      getMvpView().showLoading();

      getCompositeDisposable().add(getDataManager()
              .doFacebookLoginApiCall(new LoginRequest.FacebookLoginRequest(facebookId))
              .subscribeOn(getSchedulerProvider().io())
              .observeOn(getSchedulerProvider().ui())
              .subscribe(new Consumer<LoginResponse>() {
                 @Override
                 public void accept(LoginResponse response) throws Exception {
                    getDataManager().updateUserInfo(
                            response.getAccessToken(),
                            response.getUserId(),
                            DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER,
                            response.getUserName(),
                            response.getUserEmail(),
                            response.getGoogleProfilePicUrl());

                    if (!isViewAttached()) {
                       return;
                    }

                    getMvpView().hideLoading();
                    getMvpView().openMainActivity();

                 }
              }, new Consumer<Throwable>() {
                 @Override
                 public void accept(Throwable throwable) throws Exception {

                    if (!isViewAttached()) {
                       return;
                    }

                    getMvpView().hideLoading();

                    // handle the login error here
                    if (throwable instanceof ANError) {
                       ANError anError = (ANError) throwable;
                       handleApiError(anError);
                    }
                 }
              }));

   }

}
