package com.atwa.taxineum.ui.login;

import com.atwa.taxineum.ui.base.MvpPresenter;
import com.atwa.taxineum.ui.base.MvpView;

/**
 * Created by Ahmed Atwa on 4/2/2018.
 */

public interface LoginMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {

   void onServerLogin(String email, String password);

   void onFacebookLogin(String facebookId);


}
