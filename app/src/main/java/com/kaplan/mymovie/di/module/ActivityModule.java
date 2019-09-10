package com.kaplan.mymovie.di.module;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.kaplan.mymovie.di.ActivityContext;
import com.kaplan.mymovie.di.PerActivity;
import com.kaplan.mymovie.ui.detail.DetailMvpPresenter;
import com.kaplan.mymovie.ui.detail.DetailMvpView;
import com.kaplan.mymovie.ui.detail.DetailPresenter;
import com.kaplan.mymovie.ui.latest.PopularMvpPresenter;
import com.kaplan.mymovie.ui.latest.PopularMvpView;
import com.kaplan.mymovie.ui.latest.PopularPresenter;
import com.kaplan.mymovie.ui.main.MainMvpPresenter;
import com.kaplan.mymovie.ui.main.MainMvpView;
import com.kaplan.mymovie.ui.main.MainPresenter;
import com.kaplan.mymovie.ui.main.TvListAdapter;
import com.kaplan.mymovie.util.rx.AppSchedulerProvider;
import com.kaplan.mymovie.util.rx.SchedulerProvider;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import java.util.ArrayList;


@Module
public class ActivityModule {

  private AppCompatActivity mActivity;

  public ActivityModule(AppCompatActivity activity) {
    this.mActivity = activity;
  }

  @Provides
  @ActivityContext
  Context provideContext() {
    return mActivity;
  }

  @Provides
  AppCompatActivity provideActivity() {
    return mActivity;
  }

  @Provides
  CompositeDisposable provideCompositeDisposable() {
    return new CompositeDisposable();
  }

  @Provides
  SchedulerProvider provideSchedulerProvider() {
    return new AppSchedulerProvider();
  }

  @Provides
  LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
    return new LinearLayoutManager(activity);
  }

  @Provides
  @PerActivity
  MainMvpPresenter<MainMvpView> provideMainPresenter(
      MainPresenter<MainMvpView> presenter) {
    return presenter;
  }

  @Provides
  PopularMvpPresenter<PopularMvpView> providePopularPresenter(
      PopularPresenter<PopularMvpView> presenter) {
    return presenter;
  }

  @Provides
  DetailMvpPresenter<DetailMvpView> provideDetailPresenter(
      DetailPresenter<DetailMvpView> presenter){
    return presenter;
  }

  @Provides
  TvListAdapter provideTvListAdapter(AppCompatActivity appCompatActivity) {
    return new TvListAdapter(new ArrayList<>(),appCompatActivity);
  }
}
