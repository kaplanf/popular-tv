package com.kaplan.mymovie.data.network;

public final class ApiEndPoint {

  private ApiEndPoint() {
  }

  public static final String BASE_URL = "https://api.themoviedb.org/3/tv/";
  public static final String IMAGE_BASE = "https://image.tmdb.org/t/p/w500/";

  public static final String GET_POPULAR = "popular?";
  public static final String GET_TV_DETAIL = "{tv_id}";
}
