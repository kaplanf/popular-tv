package com.kaplan.mymovie.ui.main


import com.kaplan.mymovie.di.PerActivity
import com.kaplan.mymovie.ui.base.MvpPresenter
import com.mindorks.framework.mvp.data.database.repository.questions.Favorite
import io.reactivex.Observable

@PerActivity
interface MainMvpPresenter<V : MainMvpView> : MvpPresenter<V>
