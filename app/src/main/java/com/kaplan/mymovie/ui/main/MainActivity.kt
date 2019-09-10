package com.kaplan.mymovie.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import butterknife.ButterKnife
import com.kaplan.mymovie.R
import com.kaplan.mymovie.ui.base.BaseActivity
import com.kaplan.mymovie.ui.detail.DetailFragment
import com.kaplan.mymovie.ui.detail.DetailFragment.DetailCallBack
import com.kaplan.mymovie.ui.latest.PopularFragment
import com.kaplan.mymovie.ui.latest.PopularFragment.CallBack
import kotlinx.coroutines.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainMvpView, CallBack, DetailCallBack, CoroutineScope by CoroutineScope(Dispatchers.Default) {

    @Inject
    internal lateinit var mPresenter: MainMvpPresenter<MainMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityComponent.inject(this)

        setUnBinder(ButterKnife.bind(this))

        mPresenter.onAttach(this)

        setUp()
    }

    private fun openPopularFragment() {
        val popularFragment = PopularFragment.newInstance()
        supportFragmentManager
                .beginTransaction().addToBackStack(PopularFragment.TAG)
                .replace(R.id.content_frame, popularFragment,
                        PopularFragment.TAG)
                .commit()
    }

    private fun openDetailFragment(tvId: Int) {
        val detailFragment = DetailFragment.newInstance(tvId)
        supportFragmentManager
                .beginTransaction().addToBackStack(null)
                .add(R.id.content_frame, detailFragment,
                        DetailFragment.TAG)
                .commit()
    }

    override fun setUp() {
        openPopularFragment()
        checkCoroutine()
    }

    override fun detailClick(tvId: Int) {
        openDetailFragment(tvId)
    }

    override fun favoriteClick(tvId: Int, isFavorite: Boolean) {
        val popularFragment = supportFragmentManager.findFragmentByTag(PopularFragment.TAG) as PopularFragment?
        popularFragment!!.updateFavorites(tvId, isFavorite)
    }

    override fun onDestroy() {
        destroyCoroutine()
        super.onDestroy()
    }

    fun destroyCoroutine() {
        cancel()
    }

    fun checkCoroutine() {
        launch {
            while (true) {
                delay(60000L)
                coroutineTask()
            }
        }
    }

    fun coroutineTask() {
        println("coroutine launched")
        val popularFragment = supportFragmentManager.findFragmentByTag(PopularFragment.TAG) as PopularFragment?
        popularFragment!!.checkPopular()
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}

