
package com.atwa.taxineum;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor.Level;
import com.atwa.taxineum.data.DataManager;
import com.atwa.taxineum.di.component.ApplicationComponent;
import com.atwa.taxineum.di.component.DaggerApplicationComponent;
import com.atwa.taxineum.di.module.ApplicationModule;
import com.atwa.taxineum.utils.AppLogger;

import javax.inject.Inject;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class MvpApp extends Application {

   @Inject
   DataManager mDataManager;

   @Inject
   CalligraphyConfig mCalligraphyConfig;

   private ApplicationComponent mApplicationComponent;

   @Override
   public void onCreate() {
      super.onCreate();

      mApplicationComponent = DaggerApplicationComponent.builder()
              .applicationModule(new ApplicationModule(this)).build();

      mApplicationComponent.inject(this);

      AppLogger.init();

      AndroidNetworking.initialize(getApplicationContext());
      if (BuildConfig.DEBUG) {
         AndroidNetworking.enableLogging(Level.BODY);
      }

      CalligraphyConfig.initDefault(mCalligraphyConfig);
   }

   public ApplicationComponent getComponent() {
      return mApplicationComponent;
   }


   // Needed to replace the component with a test specific one
   public void setComponent(ApplicationComponent applicationComponent) {
      mApplicationComponent = applicationComponent;
   }
}
