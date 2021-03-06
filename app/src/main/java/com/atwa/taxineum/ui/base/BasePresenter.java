package com.atwa.taxineum.ui.base;

import android.util.Log;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.atwa.taxineum.R;
import com.atwa.taxineum.data.DataManager;
import com.atwa.taxineum.data.network.model.ApiError;
import com.atwa.taxineum.utils.AppConstants;
import com.atwa.taxineum.utils.rx.SchedulerProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Ahmed Atwa on 3/31/2018.
 */

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

   private static final String TAG = "BasePresenter";

   private final DataManager mDataManager;
   private final SchedulerProvider mSchedulerProvider;
   private final CompositeDisposable mCompositeDisposable;

   private V mMvpView;

   @Inject
   public BasePresenter(DataManager mDataManager, SchedulerProvider mSchedulerProvider, CompositeDisposable mCompositeDisposable) {
      this.mDataManager = mDataManager;
      this.mSchedulerProvider = mSchedulerProvider;
      this.mCompositeDisposable = mCompositeDisposable;
   }


   @Override
   public void onAttach(V mvpView) {
      mMvpView = mvpView;
   }

   @Override
   public void onDetach() {
      mCompositeDisposable.dispose();
      mMvpView = null;
   }

   public boolean isViewAttached() {
      return mMvpView != null;
   }

   public V getMvpView() {
      return mMvpView;
   }

   public void checkViewAttached() {
      if (!isViewAttached()) throw new MvpViewNotAttachedException();
   }

   public DataManager getDataManager() {
      return mDataManager;
   }

   public SchedulerProvider getSchedulerProvider() {
      return mSchedulerProvider;
   }

   public CompositeDisposable getCompositeDisposable() {
      return mCompositeDisposable;
   }

   @Override
   public void handleApiError(ANError error) {
      if (error == null || error.getErrorBody() == null) {
         getMvpView().onError(R.string.api_default_error);
         return;
      }

      if (error.getErrorCode() == AppConstants.API_STATUS_CODE_LOCAL_ERROR
              && error.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
         getMvpView().onError(R.string.connection_error);
         return;
      }

      if (error.getErrorCode() == AppConstants.API_STATUS_CODE_LOCAL_ERROR
              && error.getErrorDetail().equals(ANConstants.REQUEST_CANCELLED_ERROR)) {
         getMvpView().onError(R.string.api_retry_error);
         return;
      }

      final GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
      final Gson gson = builder.create();

      try {
         ApiError apiError = gson.fromJson(error.getErrorBody(), ApiError.class);

         if (apiError == null || apiError.getMessage() == null) {
            getMvpView().onError(R.string.api_default_error);
            return;
         }

         switch (error.getErrorCode()) {
            case HttpsURLConnection.HTTP_UNAUTHORIZED:
            case HttpsURLConnection.HTTP_FORBIDDEN:
               setUserAsLoggedOut();
               getMvpView().openActivityOnTokenExpire();
            case HttpsURLConnection.HTTP_INTERNAL_ERROR:
            case HttpsURLConnection.HTTP_NOT_FOUND:
            default:
               getMvpView().onError(apiError.getMessage());
         }
      } catch (JsonSyntaxException | NullPointerException e) {
         Log.e(TAG, "handleApiError", e);
         getMvpView().onError(R.string.api_default_error);
      }
   }

   @Override
   public void setUserAsLoggedOut() {

   }

   public static class MvpViewNotAttachedException extends RuntimeException {
      public MvpViewNotAttachedException() {
         super("Please call Presenter.onAttach(MvpView) before" +
                 " requesting data to the Presenter");
      }
   }

}
