package com.kaplan.mymovie.ui.latest;

import com.kaplan.mymovie.ui.base.MvpPresenter;

public interface PopularMvpPresenter<V extends PopularMvpView> extends MvpPresenter<V>  {
  void getPopularTv(int page);

  void checkPopularSort();

  void favoriteClick(int id, boolean isFavorite);

  void getFavorites();

  void isFavoritesEmpty();
}
