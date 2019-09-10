package com.kaplan.mymovie.di.module;

import android.app.Application;
import android.content.Context;
import androidx.room.Room;
import com.kaplan.mymovie.data.AppDataManager;
import com.kaplan.mymovie.data.DataManager;
import com.kaplan.mymovie.data.database.AppDatabase;
import com.kaplan.mymovie.data.network.ApiHeader;
import com.kaplan.mymovie.data.network.ApiHelper;
import com.kaplan.mymovie.data.network.AppApiHelper;
import com.kaplan.mymovie.data.prefs.AppPreferencesHelper;
import com.kaplan.mymovie.data.prefs.PreferencesHelper;
import com.kaplan.mymovie.di.ApplicationContext;
import com.kaplan.mymovie.di.DatabaseInfo;
import com.kaplan.mymovie.di.PreferenceInfo;
import com.kaplan.mymovie.util.AppConstants;
import com.mindorks.framework.mvp.data.database.repository.questions.FavoriteRepo;
import com.mindorks.framework.mvp.data.database.repository.questions.FavoriteRepository;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

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
  @Singleton
  AppDatabase provideAppDatabase() {
    return Room.databaseBuilder(mApplication, AppDatabase.class, AppConstants.DB_NAME).allowMainThreadQueries().build();
  }

  @Provides
  @Singleton
  FavoriteRepo provideFavoritesRepoHelper(AppDatabase appDatabase)
  {
    return new FavoriteRepository(appDatabase.favoritesDao());
  }

  @Provides
  @PreferenceInfo
  String providePreferenceName() {
    return AppConstants.PREF_NAME;
  }

  @Provides
  @DatabaseInfo
  String provideDatabaseName() {
    return AppConstants.DB_NAME;
  }

  @Provides
  @Singleton
  DataManager provideDataManager(AppDataManager appDataManager) {
    return appDataManager;
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
  ApiHeader.ProtectedApiHeader provideProtectedApiHeader(PreferencesHelper preferencesHelper) {
    return new ApiHeader.ProtectedApiHeader(
        preferencesHelper.getAccessToken());
  }

}
