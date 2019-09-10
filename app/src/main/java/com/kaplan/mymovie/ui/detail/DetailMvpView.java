package com.kaplan.mymovie.ui.detail;

import com.kaplan.mymovie.data.network.model.TvDetailObject;
import com.kaplan.mymovie.ui.base.MvpView;
import com.mindorks.framework.mvp.data.database.repository.questions.Favorite;
import java.util.List;

public interface DetailMvpView extends MvpView {

  void loadDetail(TvDetailObject tvDetailObject);

  public void loadFavorites(List<Favorite> favoriteList);

  public void checkFavorites(boolean isEmpty);
}
