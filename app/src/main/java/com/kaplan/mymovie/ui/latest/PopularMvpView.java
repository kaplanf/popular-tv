package com.kaplan.mymovie.ui.latest;

import com.kaplan.mymovie.data.network.model.TvObject;
import com.kaplan.mymovie.ui.base.MvpView;
import com.mindorks.framework.mvp.data.database.repository.questions.Favorite;
import java.util.List;

public interface PopularMvpView extends MvpView {
  void loadTvShows(List<TvObject> tvObjects);

  void addTvShows(List<TvObject> tvObjects);

  void checkPopularChange(List<TvObject> tvObjects);

  void loadFavorites(List<Favorite> favoriteList);

  void checkFavorites(boolean isEmpty);
}