package com.kaplan.mymovie.ui.base;

import com.kaplan.mymovie.data.DataManager;
import com.kaplan.mymovie.util.rx.SchedulerProvider;
import com.mindorks.framework.mvp.data.database.repository.questions.FavoriteRepo;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.CompositeDisposable;
import java.util.Collection;
import javax.inject.Inject;

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

  private static final String TAG = "BasePresenter";

  private final DataManager mDataManager;
  private final SchedulerProvider mSchedulerProvider;
  private final CompositeDisposable mCompositeDisposable;
  private final FavoriteRepo mFavoritesRepo;

  private V mMvpView;

  @Inject
  public BasePresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable, FavoriteRepo favoritesRepo) {
    this.mDataManager = dataManager;
    this.mSchedulerProvider = schedulerProvider;
    this.mCompositeDisposable = compositeDisposable;
    this.mFavoritesRepo = favoritesRepo;
  }

  @Override
  public void onAttach(V mvpView) {
    mMvpView = mvpView;
  }

  @Override
  public void onDetach() {
    mCompositeDisposable.dispose();
    mMvpView = null;
  }

  public boolean isViewAttached() {
    return mMvpView != null;
  }

  public V getMvpView() {
    return mMvpView;
  }

  public DataManager getDataManager() {
    return mDataManager;
  }

  public SchedulerProvider getSchedulerProvider() {
    return mSchedulerProvider;
  }

  public CompositeDisposable getCompositeDisposable() {
    return mCompositeDisposable;
  }

  public FavoriteRepo getFavoritesRepo() {
    return mFavoritesRepo;
  }

  static <T> void fromArrayToCollection(T[] a, Collection<T> c) {
    for (T o : a) {
      c.add(o); // Correct
    }
  }

  public <T> ObservableTransformer<T, T> getObservableTransformer() {
    return upstream -> upstream.subscribeOn(getSchedulerProvider().io())
        .observeOn(getSchedulerProvider().ui());
  }
}
