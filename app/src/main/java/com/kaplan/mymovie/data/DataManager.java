package com.kaplan.mymovie.data;


import com.kaplan.mymovie.data.network.ApiHelper;
import com.kaplan.mymovie.data.prefs.PreferencesHelper;

public interface DataManager extends PreferencesHelper, ApiHelper {

    void updateApiHeader(String accessToken);
}
