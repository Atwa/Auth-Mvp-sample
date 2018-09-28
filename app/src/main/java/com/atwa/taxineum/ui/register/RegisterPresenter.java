package com.atwa.taxineum.ui.register;

import com.androidnetworking.error.ANError;
import com.atwa.taxineum.R;
import com.atwa.taxineum.data.DataManager;
import com.atwa.taxineum.data.network.model.RegisterRequest;
import com.atwa.taxineum.data.network.model.RegisterResponse;
import com.atwa.taxineum.ui.base.BasePresenter;
import com.atwa.taxineum.utils.Validation;
import com.atwa.taxineum.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Ahmed Atwa on 4/3/2018.
 */

public class RegisterPresenter<V extends RegisterMvpView> extends BasePresenter<V> implements RegisterMvpPresenter<V> {

   private static final String TAG = "RegisterPresenter";

   @Inject
   public RegisterPresenter(DataManager dataManager,
                            SchedulerProvider schedulerProvider,
                            CompositeDisposable compositeDisposable) {
      super(dataManager, schedulerProvider, compositeDisposable);
   }


   @Override
   public void onRegister(String name, String phone, String email, String password, String visa) {

      if (name == null || name.isEmpty()) {
         getMvpView().onError(R.string.empty_name);
         return;
      }
      if (!Validation.isNameValid(name)) {
         getMvpView().onError(R.string.invalid_name);
         return;
      }
      if (email == null || email.isEmpty()) {
         getMvpView().onError(R.string.empty_email);
         return;
      }
      if (!Validation.isEmailValid(email)) {
         getMvpView().onError(R.string.invalid_email);
         return;
      }
      if (phone == null || phone.isEmpty()) {
         getMvpView().onError(R.string.empty_phone);
         return;
      }
      if (!Validation.isPhoneValid(phone)) {
         getMvpView().onError(R.string.invalid_phone);
         return;
      }
      if (password == null || password.isEmpty()) {
         getMvpView().onError(R.string.empty_password);
         return;
      }
      if (!Validation.isPasswordValid(password)) {
         getMvpView().onError(R.string.invalid_password);
         return;
      }
      if (!Validation.isVisaValid(visa)) {
         getMvpView().onError(R.string.invalid_visa);
         return;
      }
      if (visa == null || visa.isEmpty()) {
         visa = "null";
         return;
      }
      getMvpView().showLoading();

      getCompositeDisposable().add(getDataManager()
              .doRegisterApiCall(new RegisterRequest(name, phone, email, password, visa))
              .subscribeOn(getSchedulerProvider().io())
              .observeOn(getSchedulerProvider().ui())
              .subscribe(new Consumer<RegisterResponse>() {
                 @Override
                 public void accept(RegisterResponse response) throws Exception {
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
