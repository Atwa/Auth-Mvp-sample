package com.atwa.taxineum.di.module;

import android.app.Application;
import android.content.Context;


import com.atwa.taxineum.BuildConfig;
import com.atwa.taxineum.R;
import com.atwa.taxineum.data.AppDataManager;
import com.atwa.taxineum.data.DataManager;
import com.atwa.taxineum.data.db.AppDbHelper;
import com.atwa.taxineum.data.db.DbHelper;
import com.atwa.taxineum.data.network.ApiEndPoint;
import com.atwa.taxineum.data.network.ApiHeader;
import com.atwa.taxineum.data.network.ApiHelper;
import com.atwa.taxineum.data.network.AppApiHelper;
import com.atwa.taxineum.data.prefs.AppPreferencesHelper;
import com.atwa.taxineum.data.prefs.PreferencesHelper;
import com.atwa.taxineum.di.ApiInfo;
import com.atwa.taxineum.di.ApplicationContext;
import com.atwa.taxineum.di.DatabaseInfo;
import com.atwa.taxineum.di.PreferenceInfo;
import com.atwa.taxineum.utils.AppConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return ApiEndPoint.API_KEY;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @Singleton
    ApiHeader.ProtectedApiHeader provideProtectedApiHeader(@ApiInfo String apiKey,
                                                           PreferencesHelper preferencesHelper) {
        return new ApiHeader.ProtectedApiHeader(
                apiKey,
                preferencesHelper.getCurrentUserId(),
                preferencesHelper.getAccessToken());
    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }
}
