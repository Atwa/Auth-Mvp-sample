package com.atwa.taxineum.ui.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.atwa.taxineum.R;
import com.atwa.taxineum.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

   public static Intent getStartIntent(Context context) {
      Intent intent = new Intent(context, MainActivity.class);
      return intent;
   }


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
   }
}
