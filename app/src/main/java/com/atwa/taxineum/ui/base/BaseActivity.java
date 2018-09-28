package com.atwa.taxineum.ui.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.atwa.taxineum.MvpApp;
import com.atwa.taxineum.R;
import com.atwa.taxineum.di.component.ActivityComponent;
import com.atwa.taxineum.di.component.DaggerActivityComponent;
import com.atwa.taxineum.di.module.ActivityModule;
import com.atwa.taxineum.ui.login.LoginActivity;
import com.atwa.taxineum.utils.CommonUtils;
import com.atwa.taxineum.utils.NetworkUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Ahmed Atwa on 3/3/2018.
 */

public abstract class BaseActivity extends AppCompatActivity implements MvpView, BaseFragment.Callback {

   private Toolbar mToolBar;
   private ProgressDialog mProgressDialog;
   private RecyclerView mRecycler;

   private ActivityComponent mActivityComponent;

   private Unbinder mUnBinder;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      mActivityComponent = DaggerActivityComponent.builder()
              .activityModule(new ActivityModule(this))
              .applicationComponent(((MvpApp) getApplication()).getComponent())
              .build();
   }

   public ActivityComponent getActivityComponent() {
      return mActivityComponent;
   }

   @Override
   protected void attachBaseContext(Context newBase) {
      super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
   }

   @TargetApi(Build.VERSION_CODES.M)
   public void requestPermissionsSafely(String[] permissions, int requestCode) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
         requestPermissions(permissions, requestCode);
      }
   }

   @TargetApi(Build.VERSION_CODES.M)
   public boolean hasPermission(String permission) {
      return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
              checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
   }

   @Override
   public void setToolbar(Toolbar toolbar) {
      this.mToolBar = toolbar;
      if (mToolBar != null)
         setSupportActionBar(mToolBar);
   }

   @Override
   public void setToolbarTitle(CharSequence title) {
      if (mToolBar != null)
         mToolBar.setTitle(title);
   }

   @Override
   public void showLoading() {
      hideLoading();
      mProgressDialog = CommonUtils.showLoadingDialog(this);
   }

   @Override
   public void hideLoading() {
      if (mProgressDialog != null && mProgressDialog.isShowing()) {
         mProgressDialog.cancel();
      }
   }

   private void showSnackBar(String message) {
      Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
              message, Snackbar.LENGTH_SHORT);
      View sbView = snackbar.getView();
      TextView textView = (TextView) sbView
              .findViewById(android.support.design.R.id.snackbar_text);
      textView.setTextColor(ContextCompat.getColor(this, R.color.white));
      snackbar.show();
   }

   @Override
   public void onError(String message) {
      if (message != null) {
         showSnackBar(message);
      } else {
         showSnackBar(getString(R.string.some_error));
      }
   }

   @Override
   public void onError(@StringRes int resId) {
      onError(getString(resId));
   }

   @Override
   public void showMessage(String message) {
      if (message != null) {
         Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
      } else {
         Toast.makeText(this, getString(R.string.some_error), Toast.LENGTH_SHORT).show();
      }
   }

   @Override
   public void showMessage(@StringRes int resId) {
      showMessage(getString(resId));
   }

   @Override
   public boolean isNetworkConnected() {
      return NetworkUtils.isNetworkConnected(getApplicationContext());
   }

   @Override
   public void onFragmentAttached() {
   }

   @Override
   public void onFragmentDetached(String tag) {
   }

   @Override
   public void hideKeyboard() {
      View view = this.getCurrentFocus();
      if (view != null) {
         InputMethodManager imm = (InputMethodManager)
                 getSystemService(Context.INPUT_METHOD_SERVICE);
         imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
      }
   }

   @Override
   public void openActivityOnTokenExpire() {
      startActivity(LoginActivity.getStartIntent(this));
      finish();
   }

   public void setRecycler(RecyclerView recyclerView) {
      this.mRecycler = recyclerView;
      final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
      mRecycler.setHasFixedSize(true);
      mRecycler.setLayoutManager(layoutManager);
   }

   public void setHorizontalRecycler(RecyclerView recyclerView) {
      this.mRecycler = recyclerView;
      LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
      mRecycler.setHasFixedSize(true);
   }

   public void replaceFragment(int ContainerViewId, Fragment fragment) {
      getSupportFragmentManager().beginTransaction().replace(ContainerViewId, fragment).commit();
   }

   public void addFragmentToBackStack(int ContainerViewId, Fragment fragment) {
      getSupportFragmentManager().beginTransaction().replace(ContainerViewId, fragment).addToBackStack("TAG").commit();
   }

   public void setUnBinder(Unbinder unBinder) {
      mUnBinder = unBinder;
   }


   @Override
   protected void onDestroy() {

      if (mUnBinder != null) {
         mUnBinder.unbind();
      }
      super.onDestroy();
   }

}

