package com.kaplan.mymovie.ui.main

import com.kaplan.mymovie.data.DataManager
import com.kaplan.mymovie.ui.base.BasePresenter
import com.kaplan.mymovie.util.rx.SchedulerProvider
import com.mindorks.framework.mvp.data.database.repository.questions.Favorite
import com.mindorks.framework.mvp.data.database.repository.questions.FavoriteRepo
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.ArrayList
import javax.inject.Inject

class MainPresenter<V : MainMvpView> @Inject
constructor(dataManager: DataManager,
            schedulerProvider: SchedulerProvider,
            compositeDisposable: CompositeDisposable, favoriteRepo: FavoriteRepo) : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable, favoriteRepo), MainMvpPresenter<V> {
    companion object {

        private val TAG = "MainPresenter"
    }
}
