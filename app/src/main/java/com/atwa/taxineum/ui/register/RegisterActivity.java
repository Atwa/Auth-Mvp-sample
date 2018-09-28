package com.atwa.taxineum.ui.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.atwa.taxineum.R;
import com.atwa.taxineum.ui.base.BaseActivity;
import com.atwa.taxineum.ui.main.MainActivity;

public class RegisterActivity extends BaseActivity implements RegisterMvpView {

   public static Intent getStartIntent(Context context) {
      Intent intent = new Intent(context, RegisterActivity.class);
      return intent;
   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_register);
   }

   @Override
   public void openMainActivity() {

   }
}
