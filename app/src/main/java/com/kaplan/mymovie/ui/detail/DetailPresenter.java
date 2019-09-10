package com.kaplan.mymovie.ui.detail;

import com.kaplan.mymovie.data.DataManager;
import com.kaplan.mymovie.ui.base.BasePresenter;
import com.kaplan.mymovie.util.AppConstants;
import com.kaplan.mymovie.util.rx.SchedulerProvider;
import com.mindorks.framework.mvp.data.database.repository.questions.Favorite;
import com.mindorks.framework.mvp.data.database.repository.questions.FavoriteRepo;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;

public class DetailPresenter<V extends DetailMvpView> extends BasePresenter<V> implements
    DetailMvpPresenter<V> {

  private static final String TAG = "DetailPresenter";

  @Inject
  public DetailPresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable, FavoriteRepo favoriteRepo) {
    super(dataManager, schedulerProvider, compositeDisposable, favoriteRepo);
  }

  @Override
  public void getTvDetail(int tvId) {
    String tvIdString = Integer.toString(tvId);
    getMvpView().showLoading();
    getCompositeDisposable().add(
        getDataManager().getDetail(tvIdString, AppConstants.API_KEY)
            .subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui()).subscribe(
            tvDetailObject -> {
              getMvpView().hideLoading();
              getMvpView().loadDetail(tvDetailObject);
            }, throwable -> getMvpView().hideLoading()));
  }

  @Override
  public void favoriteClick(int id, boolean isFavorite) {
    Favorite favorite = new Favorite(id, isFavorite);
    if (favorite.isFavorite()) {
      getCompositeDisposable()
          .add(getFavoritesRepo().insertFavorite(favorite).subscribeOn(getSchedulerProvider().io())
              .observeOn(getSchedulerProvider().ui())
              .subscribe(aBoolean -> getMvpView().showMessage("Added to Favorites"),
                  throwable -> getMvpView()
                      .showMessage("There was a problem on favorite operation")));
    } else {
      getCompositeDisposable()
          .add(getFavoritesRepo().deleteFavorite(favorite).subscribeOn(getSchedulerProvider().io())
              .observeOn(getSchedulerProvider().ui())
              .subscribe(aBoolean -> getMvpView().showMessage("Deleted from Favorites")));
    }

  }

  @Override
  public void getFavorites() {
    getFavoritesRepo().loadFavorites().subscribeOn(getSchedulerProvider().io())
        .observeOn(getSchedulerProvider().ui())
        .subscribe(favorites -> getMvpView().loadFavorites(favorites));
  }

  @Override
  public void isFavoritesEmpty() {
    getFavoritesRepo().isFavoritesRepoEmpty().subscribeOn(getSchedulerProvider().io())
        .observeOn(getSchedulerProvider().ui())
        .subscribe(isEmpty -> getMvpView().checkFavorites(isEmpty));
  }
}
