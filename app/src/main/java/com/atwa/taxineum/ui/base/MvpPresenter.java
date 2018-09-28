package com.atwa.taxineum.ui.base;

import com.androidnetworking.error.ANError;

/**
 * Created by Ahmed Atwa on 3/31/2018.
 */

public interface MvpPresenter<V extends MvpView> {

   void onAttach(V mvpView);

   void onDetach();

   void handleApiError(ANError error);

   void setUserAsLoggedOut();

}
