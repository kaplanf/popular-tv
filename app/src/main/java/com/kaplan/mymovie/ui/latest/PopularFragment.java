package com.kaplan.mymovie.ui.latest;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.kaplan.mymovie.R;
import com.kaplan.mymovie.data.network.model.TvObject;
import com.kaplan.mymovie.di.component.ActivityComponent;
import com.kaplan.mymovie.ui.base.BaseFragment;
import com.kaplan.mymovie.ui.main.TvListAdapter;
import com.kaplan.mymovie.util.NetworkUtils;
import com.mindorks.framework.mvp.data.database.repository.questions.Favorite;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class PopularFragment extends BaseFragment implements PopularMvpView,
    TvListAdapter.Callback {

  public static final String TAG = "PopularFragment";

  private int lastVisibleItem, totalItemCount;
  private int visibleThreshold = 20;

  private boolean isFavoritesEmpty = true;
  private ArrayList<Favorite> favorites;

  @Inject
  PopularMvpPresenter<PopularMvpView> mPresenter;

  @Inject
  LinearLayoutManager mLayoutManager;

  @Inject
  TvListAdapter adapter;

  @BindView(R.id.movie_recycler_view)
  RecyclerView movieRecyclerview;

  private ArrayList<TvObject> tvObjects;

  private int page = 1;

  public interface CallBack {

    void detailClick(int tvId);
  }

  private CallBack callBack;

  public static PopularFragment newInstance() {
    Bundle args = new Bundle();
    PopularFragment fragment = new PopularFragment();
    return fragment;
  }

  public void onAttach(Context context) {
    super.onAttach(context);
    callBack = (CallBack) context;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
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
  protected void setUp(View view) {
    mPresenter.isFavoritesEmpty();
    if (NetworkUtils.isNetworkConnected(getActivity())) {
      mPresenter.getPopularTv(page);
    }
  }

  @Override
  public void loadTvShows(List<TvObject> tvs) {
    tvObjects = (ArrayList<TvObject>) tvs;

    if (!isFavoritesEmpty) {
      mPresenter.getFavorites();
    }
    adapter.setCallback(this);
    adapter.addItems(tvObjects);
    mLayoutManager.setOrientation(RecyclerView.VERTICAL);
    movieRecyclerview.setLayoutManager(mLayoutManager);
    movieRecyclerview.setAdapter(adapter);

    movieRecyclerview
        .addOnScrollListener(getOnScrollListener());
  }

  @Override
  public void addTvShows(List<TvObject> tvs) {
    if (!isFavoritesEmpty) {
      mPresenter.getFavorites();
    }
    modifyFavorites((ArrayList<TvObject>) tvs);
    tvObjects.addAll(tvs);
    adapter.insertItems(tvs);
  }

  @Override
  public void onLoadMore() {
    page++;
    mPresenter.getPopularTv(page);
  }

  @Override
  public void onItemClicked(TvObject tvObject) {
    callBack.detailClick(tvObject.getId());
  }

  @Override
  public void onFavoriteClicked(int tvId, boolean favorite) {
    mPresenter.favoriteClick(tvId, favorite);
  }

  @Override
  public void loadFavorites(List<Favorite> favoriteList) {
    favorites = (ArrayList<Favorite>) favoriteList;
    if (tvObjects != null && tvObjects.size() > 0) {
      modifyFavorites(tvObjects);
      adapter.notifyDataSetChanged();
    }
  }

  @Override
  public void checkFavorites(boolean isEmpty) {
    isFavoritesEmpty = isEmpty;
  }

  private void modifyFavorites(ArrayList<TvObject> tvObjectArrayList) {
    if (!isFavoritesEmpty) {
      Observable.fromIterable(tvObjectArrayList).map(tvObject -> {
        if (favorites.stream().anyMatch(favorite -> tvObject.getId() == favorite.getId())) {
          tvObject.setFavorite(true);
        }
        return tvObject;
      }).toList()
          .subscribe((tvObjects1, throwable) -> tvObjects = (ArrayList<TvObject>) tvObjects1);
    }
  }

  public void updateFavorites(int tvId, boolean isFavorite) {
    Observable.fromIterable(tvObjects).map(tvObject -> {
      if (tvObject.getId() == tvId) {
        tvObject.setFavorite(isFavorite);
      }
      return tvObject;
    }).toList()
        .subscribe((tvObjects1, throwable) -> tvObjects = (ArrayList<TvObject>) tvObjects1);
    adapter.notifyDataSetChanged();
  }

  @Override
  public void checkPopularChange(List<TvObject> tvObjectsUpdated) {
    List<TvObject> sortChanged = new ArrayList<>();
    tvObjects.stream().limit(tvObjectsUpdated.size()).forEach(
        tvObject -> tvObjectsUpdated.stream()
            .filter(tvObject1 -> tvObject1.getId() == tvObject.getId()).
                forEach(sortChanged::add));

    if (sortChanged.size() != tvObjectsUpdated.size()) {
      updatePopularList(tvObjectsUpdated);
    }
  }

  private void updatePopularList(List<TvObject> tvObjectsUpdated) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setTitle(R.string.update_alert_title);
    builder.setMessage(R.string.update_alert_message);
    builder.setPositiveButton(R.string.ok, (dialog, id) -> {
      visibleThreshold = 20;
      loadTvShows(tvObjectsUpdated);
    });
    builder.setNegativeButton(R.string.cancel, (dialog, id) -> {
    });
    AlertDialog dialog = builder.create();
    dialog.show();
  }

  public void checkPopular() {
    mPresenter.checkPopularSort();
  }

  private OnScrollListener getOnScrollListener() {
    OnScrollListener onScrollListener = new OnScrollListener() {
      @Override
      public void onScrolled(@androidx.annotation.NonNull RecyclerView recyclerView, int dx,
          int dy) {
        super.onScrolled(recyclerView, dx, dy);
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
            .getLayoutManager();
        totalItemCount = linearLayoutManager.getItemCount();
        lastVisibleItem = linearLayoutManager
            .findLastVisibleItemPosition();
        if (totalItemCount - 1 <= (lastVisibleItem) && totalItemCount >= visibleThreshold) {
          visibleThreshold = visibleThreshold + 20;
          onLoadMore();
        }
      }
    };
    return onScrollListener;
  }
}
