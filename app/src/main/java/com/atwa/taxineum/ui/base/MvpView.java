package com.atwa.taxineum.ui.base;

import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

/**
 * Created by Ahmed Atwa on 3/31/2018.
 */

public interface MvpView {

   void openActivityOnTokenExpire();

   void showLoading();

   void hideLoading();

   boolean isNetworkConnected();

   void hideKeyboard();

   void setToolbar(Toolbar toolbar);

   void setToolbarTitle(CharSequence title);

   void onError(String message);

   void onError(@StringRes int resId);

   void showMessage(String message);

   void showMessage(@StringRes int resId);

}
