package com.atwa.taxineum.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.atwa.taxineum.R;
import com.atwa.taxineum.ui.base.BaseActivity;
import com.atwa.taxineum.ui.login.LoginActivity;
import com.atwa.taxineum.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements SplashMvpView {

   @Inject
   SplashMvpPresenter<SplashMvpView> mPresenter;

   public static Intent getStartIntent(Context context) {
      Intent intent = new Intent(context, SplashActivity.class);
      return intent;
   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      getActivityComponent().inject(this);

      setUnBinder(ButterKnife.bind(this));

      mPresenter.onAttach(SplashActivity.this);
   }

   @Override
   protected void onDestroy() {
      mPresenter.onDetach();
      super.onDestroy();
   }

   @Override
   public void openLoginActivity() {
      Intent intent = LoginActivity.getStartIntent(SplashActivity.this);
      startActivity(intent);
      finish();
   }

   @Override
   public void openMainActivity() {
      Intent intent = MainActivity.getStartIntent(SplashActivity.this);
      startActivity(intent);
      finish();
   }
}
