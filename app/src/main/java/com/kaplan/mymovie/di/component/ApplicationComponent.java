package com.kaplan.mymovie.di.component;

import android.app.Application;
import android.content.Context;
import com.kaplan.mymovie.PopularTvApplication;
import com.kaplan.mymovie.PopularTvApplication;
import com.kaplan.mymovie.data.DataManager;
import com.kaplan.mymovie.di.ApplicationContext;
import com.kaplan.mymovie.di.module.ApplicationModule;
import com.mindorks.framework.mvp.data.database.repository.questions.FavoriteRepo;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(PopularTvApplication app);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();

    FavoriteRepo getFavoriteRepo();
}