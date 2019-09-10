package com.kaplan.mymovie.data;


import android.content.Context;
import com.kaplan.mymovie.data.network.ApiHeader;
import com.kaplan.mymovie.data.network.ApiHelper;
import com.kaplan.mymovie.data.network.model.TvDetailObject;
import com.kaplan.mymovie.data.network.model.TvResponse;
import com.kaplan.mymovie.data.prefs.PreferencesHelper;
import com.kaplan.mymovie.di.ApplicationContext;
import io.reactivex.Observable;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppDataManager implements DataManager {

    private static final String TAG = "AppDataManager";

    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          PreferencesHelper preferencesHelper,
                          ApiHelper apiHelper) {
        mContext = context;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
    }

    @Override
    public Observable<TvResponse> getPopular(String apikey,String page) {
        return mApiHelper.getPopular(apikey,page);
    }

    @Override
    public Observable<TvDetailObject> getDetail(String tvId,String apikey) {
        return mApiHelper.getDetail(tvId,apikey);
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }

    @Override
    public void updateApiHeader(String accessToken) {
        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }
}
