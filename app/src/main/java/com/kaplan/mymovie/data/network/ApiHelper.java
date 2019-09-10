package com.kaplan.mymovie.data.network;

import com.kaplan.mymovie.data.network.model.TvDetailObject;
import com.kaplan.mymovie.data.network.model.TvResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiHelper {

    ApiHeader getApiHeader();

    @GET(ApiEndPoint.GET_POPULAR)
    Observable<TvResponse> getPopular(@Query("api_key") String apikey,
        @Query("page") String page);

    @GET(ApiEndPoint.GET_TV_DETAIL)
    Observable<TvDetailObject> getDetail(@Path("tv_id") String tvId, @Query("api_key") String apikey);

}
