package com.kaplan.mymovie;

import android.app.Application;
import com.kaplan.mymovie.data.DataManager;
import com.kaplan.mymovie.di.component.ApplicationComponent;
import com.kaplan.mymovie.di.component.DaggerApplicationComponent;
import com.kaplan.mymovie.di.module.ApplicationModule;
import javax.inject.Inject;

public class PopularTvApplication extends Application {

    @Inject
    DataManager mDataManager;

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);

    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
