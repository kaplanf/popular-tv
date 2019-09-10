package com.kaplan.mymovie.ui.latest;

import com.kaplan.mymovie.data.DataManager;
import com.kaplan.mymovie.ui.base.BasePresenter;
import com.kaplan.mymovie.util.AppConstants;
import com.kaplan.mymovie.util.rx.SchedulerProvider;
import com.mindorks.framework.mvp.data.database.repository.questions.Favorite;
import com.mindorks.framework.mvp.data.database.repository.questions.FavoriteRepo;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;

public class PopularPresenter<V extends PopularMvpView> extends BasePresenter<V> implements
    PopularMvpPresenter<V> {

  private static final String TAG = "PopularPresenter";

  @Inject
  public PopularPresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable, FavoriteRepo favoriteRepo) {
    super(dataManager, schedulerProvider, compositeDisposable, favoriteRepo);
  }

  @Override
  public void getPopularTv(int page) {
    String pageString = Integer.toString(page);
    getMvpView().showLoading();
    getCompositeDisposable().add(
        getDataManager().getPopular(AppConstants.API_KEY, pageString)
            .subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui()).subscribe(
            tvResponse -> {
              getMvpView().hideLoading();
              if (page > 1) {
                getMvpView().addTvShows(tvResponse.getResults());
              } else {
                getMvpView().loadTvShows(tvResponse.getResults());
              }
            }, throwable ->
                getMvpView().hideLoading()));
  }

  @Override
  public void checkPopularSort() {
    getCompositeDisposable().add(
        getDataManager().getPopular(AppConstants.API_KEY, "1")
            .subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui()).subscribe(
            tvResponse -> {
              getMvpView().checkPopularChange(tvResponse.getResults());

            }, throwable ->
                System.out.println(throwable.getMessage())));
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
