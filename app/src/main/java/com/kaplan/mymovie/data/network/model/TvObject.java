package com.kaplan.mymovie.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class TvObject implements Serializable {

  @SerializedName("id")
  private int id;

  @SerializedName("original_name")
  private String originalName;

  @SerializedName("name")
  private String name;

  @SerializedName("popularity")
  private float popularity;

  @SerializedName("vote_count")
  private float voteCount;

  @SerializedName("first_air_date")
  private String firstAirDate;

  @SerializedName("backdrop_path")
  private String backdropPath;

  @SerializedName("original_language")
  private String originalLanguage;

  @SerializedName("vote_average")
  private double voteAverage;

  @SerializedName("overview")
  private String overview;

  @SerializedName("poster_path")
  private String posterPath;

  private boolean isFavorite;

  public int getId() {
    return id;
  }

  public String getOriginalName() {
    return originalName;
  }

  public String getName() {
    return name;
  }

  public float getPopularity() {
    return popularity;
  }

  public float getVoteCount() {
    return voteCount;
  }

  public String getFirstAirDate() {
    return firstAirDate;
  }

  public String getBackdropPath() {
    return backdropPath;
  }

  public String getOriginalLanguage() {
    return originalLanguage;
  }

  public double getVoteAverage() {
    return voteAverage;
  }

  public String getOverview() {
    return overview;
  }

  public String getPosterPath() {
    return posterPath;
  }

  public boolean isFavorite() {
    return isFavorite;
  }

  public void setFavorite(boolean favorite) {
    isFavorite = favorite;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }


}
