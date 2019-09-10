package com.kaplan.mymovie.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class TvDetailObject {

  @SerializedName("first_air_date")
  private String firstAirDate;

  @SerializedName("genres")
  ArrayList<Genre> genres = new ArrayList<Genre>();

  @SerializedName("id")
  private float id;

  @SerializedName("last_air_date")
  private String lastAirDate;

  @SerializedName("name")
  private String name;

  @SerializedName("number_of_episodes")
  private int numberOfEpisodes;

  @SerializedName("number_of_seasons")
  private int numberOfSeasons;

  @SerializedName("original_language")
  private String originalLanguage;

  @SerializedName("original_name")
  private String originalName;

  @SerializedName("overview")
  private String overview;

  @SerializedName("popularity")
  private float popularity;

  @SerializedName("poster_path")
  private String posterPath;

  @SerializedName("vote_average")
  private double voteAverage;

  @SerializedName("vote_count")
  private double voteCount;


  public class Genre {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    public int getId() {
      return id;
    }

    public String getName() {
      return name;
    }
  }

  public String getFirstAirDate() {
    return firstAirDate;
  }

  public ArrayList<Genre> getGenres() {
    return genres;
  }

  public float getId() {
    return id;
  }

  public String getLastAirDate() {
    return lastAirDate;
  }

  public String getName() {
    return name;
  }

  public int getNumberOfEpisodes() {
    return numberOfEpisodes;
  }

  public int getNumberOfSeasons() {
    return numberOfSeasons;
  }

  public String getOriginalLanguage() {
    return originalLanguage;
  }

  public String getOriginalName() {
    return originalName;
  }

  public String getOverview() {
    return overview;
  }

  public float getPopularity() {
    return popularity;
  }

  public String getPosterPath() {
    return posterPath;
  }

  public double getVoteAverage() {
    return voteAverage;
  }

  public double getVoteCount() {
    return voteCount;
  }

}
