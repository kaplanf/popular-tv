package com.kaplan.mymovie.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

public class TvResponse implements Serializable {

  @SerializedName("page")
  private int page;

  @SerializedName("total_results")
  private int totalResults;

  @SerializedName("total_pages")
  private int totalPages;

  @SerializedName("results")
  private ArrayList<TvObject> results;

  private int category;

  public int getPage() {
    return page;
  }

  public int getTotalResults() {
    return totalResults;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public ArrayList<TvObject> getResults() {
    return results;
  }

  public void setCategory(int category) {
    this.category = category;
  }

}
