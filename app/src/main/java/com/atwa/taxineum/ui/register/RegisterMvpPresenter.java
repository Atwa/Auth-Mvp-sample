package com.atwa.taxineum.ui.register;

import com.atwa.taxineum.ui.base.MvpPresenter;

/**
 * Created by Ahmed Atwa on 4/3/2018.
 */

public interface RegisterMvpPresenter<V extends RegisterMvpView> extends MvpPresenter<V> {

   void onRegister(String name,String phone,String email,String password,String visa);

}
