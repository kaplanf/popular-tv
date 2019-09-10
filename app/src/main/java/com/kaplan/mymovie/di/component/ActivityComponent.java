package com.kaplan.mymovie.di.component;

import com.kaplan.mymovie.di.PerActivity;
import com.kaplan.mymovie.di.module.ActivityModule;
import com.kaplan.mymovie.ui.detail.DetailFragment;
import com.kaplan.mymovie.ui.latest.PopularFragment;
import com.kaplan.mymovie.ui.main.MainActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(PopularFragment fragment);

    void inject(DetailFragment fragment);
}
