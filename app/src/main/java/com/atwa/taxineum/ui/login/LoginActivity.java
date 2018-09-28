package com.atwa.taxineum.ui.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.atwa.taxineum.R;
import com.atwa.taxineum.ui.base.BaseActivity;
import com.atwa.taxineum.ui.main.MainActivity;
import com.atwa.taxineum.ui.register.RegisterActivity;
import com.atwa.taxineum.ui.splash.SplashActivity;

public class LoginActivity extends BaseActivity implements LoginMvpView {


   public static Intent getStartIntent(Context context) {
      Intent intent = new Intent(context, LoginActivity.class);
      return intent;
   }


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_login);
   }

   @Override
   public void openMainActivity() {
      Intent intent = MainActivity.getStartIntent(LoginActivity.this);
      startActivity(intent);
      finish();
   }

   public void openRegisterActivity() {
      Intent intent = RegisterActivity.getStartIntent(LoginActivity.this);
      startActivity(intent);
      finish();
   }

}
