package com.atwa.taxineum.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.atwa.taxineum.R;
import com.atwa.taxineum.di.component.ActivityComponent;
import com.atwa.taxineum.utils.CommonUtils;


import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ahmed Atwa on 3/3/2018.
 */

public abstract class BaseFragment extends Fragment implements MvpView {

   private BaseActivity mActivity;
   private Unbinder mUnBinder;
   private ProgressDialog mProgressDialog;

   @Override
   public void onAttach(Context context) {
      super.onAttach(context);
      if (context instanceof BaseActivity) {
         BaseActivity activity = (BaseActivity) context;
         this.mActivity = activity;
      }
   }

   @Override
   public void onDetach() {
      mActivity = null;
      super.onDetach();
   }

   protected abstract int getLayoutResource();

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view = inflater.inflate(getLayoutResource(), container, false);
      return view;
   }

   @Override
   public void onViewCreated(View view, Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      ButterKnife.bind(this, view);
   }

   @Override
   public void setToolbar(Toolbar toolbar) {
      if (mActivity != null) {
         mActivity.setToolbar(toolbar);
      }
   }

   @Override
   public void setToolbarTitle(CharSequence title) {
      if (mActivity != null) {
         mActivity.setToolbarTitle(title);
      }
   }

   public void setRecycler(RecyclerView recyclerView) {
      if (mActivity != null) {
        mActivity.setRecycler(recyclerView);
      }
   }

   public void setHorizontalRecycler(RecyclerView recyclerView) {
      if (mActivity != null) {
         mActivity.setHorizontalRecycler(recyclerView);
      }
   }

   @Override
   public void showLoading() {
      hideLoading();
      mProgressDialog = CommonUtils.showLoadingDialog(this.getContext());
   }

   @Override
   public void hideLoading() {
      if (mProgressDialog != null && mProgressDialog.isShowing()) {
         mProgressDialog.cancel();
      }
   }

   @Override
   public void onError(String message) {
      if (mActivity != null) {
         mActivity.onError(message);
      }
   }

   @Override
   public void onError(@StringRes int resId) {
      if (mActivity != null) {
         mActivity.onError(resId);
      }
   }

   @Override
   public void showMessage(String message) {
      if (mActivity != null) {
         mActivity.showMessage(message);
      }
   }

   @Override
   public void showMessage(@StringRes int resId) {
      if (mActivity != null) {
         mActivity.showMessage(resId);
      }
   }

   @Override
   public boolean isNetworkConnected() {
      if (mActivity != null) {
         return mActivity.isNetworkConnected();
      }
      return false;
   }

   @Override
   public void hideKeyboard() {
      if (mActivity != null) {
         mActivity.hideKeyboard();
      }
   }

   public void replaceChildFragment(int ContainerViewId, Fragment fragment) {
      if (mActivity != null)
         getChildFragmentManager().beginTransaction().replace(ContainerViewId, fragment).commit();
   }

   public void addChildFragmentToBackstack(int ContainerViewId, Fragment fragment) {
      if (mActivity != null)
         getChildFragmentManager().beginTransaction().replace(ContainerViewId, fragment).addToBackStack("TAG").commit();
   }

   @Override
   public void openActivityOnTokenExpire() {
      if (mActivity != null) {
         mActivity.openActivityOnTokenExpire();
      }
   }

   public BaseActivity getBaseActivity() {
      return mActivity;
   }

   public ActivityComponent getActivityComponent() {
      if (mActivity != null) {
         return mActivity.getActivityComponent();
      }
      return null;
   }

   public void setUnBinder(Unbinder unBinder) {
      mUnBinder = unBinder;
   }

   @Override
   public void onDestroy() {
      if (mUnBinder != null) {
         mUnBinder.unbind();
      }
      super.onDestroy();
   }

   public interface Callback {

      void onFragmentAttached();

      void onFragmentDetached(String tag);
   }

}
