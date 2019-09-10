package com.kaplan.mymovie.ui.detail;

import com.kaplan.mymovie.ui.base.MvpPresenter;

public interface DetailMvpPresenter<V extends DetailMvpView> extends MvpPresenter<V> {

  void getTvDetail(int tvId);

  void favoriteClick(int id, boolean isFavorite);

  void getFavorites();

  void isFavoritesEmpty();
}
