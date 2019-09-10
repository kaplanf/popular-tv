package com.kaplan.mymovie.ui.detail;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.kaplan.mymovie.R;
import com.kaplan.mymovie.data.network.ApiEndPoint;
import com.kaplan.mymovie.data.network.model.TvDetailObject;
import com.kaplan.mymovie.data.network.model.TvDetailObject.Genre;
import com.kaplan.mymovie.di.component.ActivityComponent;
import com.kaplan.mymovie.ui.base.BaseFragment;
import com.mindorks.framework.mvp.data.database.repository.questions.Favorite;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class DetailFragment extends BaseFragment implements DetailMvpView {

  public static final String TAG = "DetailFragment";

  @Inject
  DetailMvpPresenter<DetailMvpView> mPresenter;

  @BindView(R.id.detail_image)
  ImageView detailImage;

  @BindView(R.id.detail_title)
  TextView titleText;

  @BindView(R.id.detail_release_date)
  TextView releaseDateText;

  @BindView(R.id.detail_info)
  TextView infoText;

  @BindView(R.id.detail_seasons)
  TextView detailSeasons;

  @BindView(R.id.detail_genres)
  TextView detailGenres;

  @BindView(R.id.detail_overview)
  TextView overViewText;

  @BindView(R.id.detail_favorite)
  ImageView favoriteIcon;

  int tvId;
  private boolean isFavoritesEmpty;
  private ArrayList<Favorite> favorites;
  private boolean isFavorite = false;

  public interface DetailCallBack {

    void favoriteClick(int tvId, boolean isFavorite);
  }

  private DetailCallBack callBack;

  public static DetailFragment newInstance(int tvId) {
    Bundle args = new Bundle();
    args.putSerializable("tvId", tvId);
    DetailFragment fragment = new DetailFragment();
    fragment.setArguments(args);
    return fragment;
  }

  public void onAttach(Context context) {
    super.onAttach(context);
    callBack = (DetailCallBack) context;
    tvId = (int) getArguments().getSerializable("tvId");
  }

  @Override
  protected void setUp(View view) {
    mPresenter.getTvDetail(tvId);
    mPresenter.isFavoritesEmpty();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_detail, container, false);
    ActivityComponent component = getActivityComponent();
    setHasOptionsMenu(true);
    if (component != null) {
      component.inject(this);
      setUnBinder(ButterKnife.bind(this, view));
      mPresenter.onAttach(this);
    }
    return view;
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    menu.clear();
  }

  @Override
  public void loadDetail(TvDetailObject tvDetailObject) {
    titleText.setText(tvDetailObject.getName());
    releaseDateText.setText(tvDetailObject.getFirstAirDate());
    infoText.setText(
        "Voted " + Double.toString(tvDetailObject.getVoteAverage()) + " points by "
            + tvDetailObject
            .getVoteCount() + " users");
    overViewText.setText(tvDetailObject.getOverview());

    detailSeasons.setText(
        tvDetailObject.getNumberOfSeasons() + " Seasons\n" + tvDetailObject.getNumberOfEpisodes()
            + " Episodes");

    StringBuilder sb = new StringBuilder();
    sb.append("Genres:");
    for (Genre genre : tvDetailObject.getGenres()) {
      sb.append("\n" + genre.getName());
    }
    detailGenres.setText(sb.toString());

    GradientDrawable gradientDrawable = new GradientDrawable();
    gradientDrawable.setShape(GradientDrawable.RECTANGLE);
    gradientDrawable.setColor(getActivity().getResources().getColor(R.color.dark_green, null));
    Picasso.get().load(ApiEndPoint.IMAGE_BASE + tvDetailObject.getPosterPath())
        .networkPolicy(NetworkPolicy.OFFLINE).placeholder(gradientDrawable)
        .into(detailImage);
  }

  @Override
  public void loadFavorites(List<Favorite> favoriteList) {
    favorites = (ArrayList<Favorite>) favoriteList;
    isFavorite = favorites.stream().anyMatch(favorite -> favorite.getId() == tvId);
    if (isFavorite) {
      favoriteIcon.setSelected(isFavorite);
    }
  }

  @Override
  public void checkFavorites(boolean isEmpty) {
    isFavoritesEmpty = isEmpty;
    mPresenter.getFavorites();
  }

  @OnClick(R.id.detail_favorite)
  void favoriteClick(View v) {
    mPresenter.favoriteClick(tvId, !isFavorite);
    favoriteIcon.setSelected(!isFavorite);
    callBack.favoriteClick(tvId, !isFavorite);
  }
}
