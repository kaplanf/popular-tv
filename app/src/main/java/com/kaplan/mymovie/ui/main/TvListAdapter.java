package com.kaplan.mymovie.ui.main;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.kaplan.mymovie.R;
import com.kaplan.mymovie.data.network.ApiEndPoint;
import com.kaplan.mymovie.data.network.model.TvObject;
import com.kaplan.mymovie.ui.base.BaseViewHolder;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import java.util.List;

public class TvListAdapter extends RecyclerView.Adapter<BaseViewHolder> {
  private List<TvObject> tvs;

  private Callback mCallback;
  private Context mContext;

  public static final int VIEW_TYPE_EMPTY = 0;
  public static final int VIEW_TYPE_NORMAL = 1;

  public TvListAdapter(List<TvObject> tvs, Context context) {
    this.tvs = tvs;
    this.mContext = context;
  }

  public void setCallback(Callback callback) {
    mCallback = callback;
  }

  @Override
  public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    switch (viewType) {
      case VIEW_TYPE_NORMAL:
        return new TvListAdapter.ViewHolder(
            LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.movie_item_view, parent, false));
      case VIEW_TYPE_EMPTY:
      default:
        return new TvListAdapter.EmptyViewHolder(
            LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.movie_empty_view, parent, false));
    }
  }

  @Override
  public void onBindViewHolder(BaseViewHolder holder, int position) {
    holder.onBind(position);
  }

  @Override
  public int getItemCount() {
    if (tvs != null && tvs.size() > 1) {
      return tvs.size();
    } else {
      return 1;
    }
  }

  @Override
  public int getItemViewType(int position) {
    if (tvs != null && tvs.size() > 0) {
      return VIEW_TYPE_NORMAL;
    } else {
      return VIEW_TYPE_EMPTY;
    }
  }

  public void addItems(List<TvObject> listObjects) {
    tvs.clear();
    tvs.addAll(listObjects);
    cacheImages(listObjects);
    notifyDataSetChanged();
  }

  public void insertItems(List<TvObject> listObjects) {

    tvs.addAll(listObjects);
    cacheImages(listObjects);
    notifyItemInserted(getItemCount() - 1);
  }

  public class ViewHolder extends BaseViewHolder {

    @BindView(R.id.movie_item_card)
    CardView cardView;

    @BindView(R.id.movie_item_image)
    ImageView itemImage;

    @BindView(R.id.movie_item_overview)
    TextView overView;

    @BindView(R.id.movie_item_title)
    TextView title;

    @BindView(R.id.movie_item_release_date)
    TextView releaseDate;

    @BindView(R.id.movie_item_vote_average)
    TextView voteAverage;

    @BindView(R.id.movie_item_favorite)
    ImageView favoriteButton;

    @OnClick({R.id.movie_item_image, R.id.movie_item_overview, R.id.movie_item_title,
        R.id.movie_item_vote_average})
    void onItemClick(View v) {
      mCallback.onItemClicked(tvs.get(getAdapterPosition()));
    }

    @OnClick(R.id.movie_item_favorite)
    void favoriteClick(View v) {
      boolean isFavorite = tvs.get(getAdapterPosition()).isFavorite();
      tvs.get(getAdapterPosition()).setFavorite(!isFavorite);
      favoriteButton.setSelected(!isFavorite);
      mCallback.onFavoriteClicked(tvs.get(getAdapterPosition()).getId(), !isFavorite);
    }


    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    @Override
    protected void clear() {

    }

    public void onBind(final int position) {
      super.onBind(position);

      TvObject tvObject = tvs.get(position);
      title.setText(tvObject.getName());
      overView.setText(tvObject.getOverview());
      voteAverage.setText(
          "Voted " + Double.toString(tvObject.getVoteAverage()) + " points by " + tvObject
              .getVoteCount() + " users");
      releaseDate.setText(tvObject.getFirstAirDate());

      GradientDrawable gradientDrawable = new GradientDrawable();
      gradientDrawable.setShape(GradientDrawable.RECTANGLE);
      gradientDrawable.setColor(mContext.getResources().getColor(R.color.dark_green, null));
      Picasso.get().load(ApiEndPoint.IMAGE_BASE + tvObject.getPosterPath())
          .networkPolicy(NetworkPolicy.OFFLINE).placeholder(gradientDrawable)
          .into(itemImage);
      favoriteButton.setSelected(tvObject.isFavorite());
    }
  }

  public class EmptyViewHolder extends BaseViewHolder {

    public EmptyViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    @Override
    protected void clear() {

    }
  }

  private void cacheImages(List<TvObject> listObjects) {
    for (TvObject tvObject : listObjects) {
      Picasso.get().load(ApiEndPoint.IMAGE_BASE + tvObject.getPosterPath()).fetch();
    }
  }

  public interface Callback {

    void onLoadMore();

    void onItemClicked(TvObject tvObject);

    void onFavoriteClicked(int tvId, boolean favorite);
  }

}
