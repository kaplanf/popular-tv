package com.mindorks.framework.mvp.data.database.repository.questions

import io.reactivex.Observable
import javax.inject.Inject

class FavoriteRepository @Inject internal constructor(private val favoritesDao: FavoritesDao) : FavoriteRepo {

    override fun isFavoritesRepoEmpty(): Observable<Boolean> = Observable.fromCallable({ favoritesDao.loadAll().isEmpty() })

    override fun insertFavorite(favorite: Favorite): Observable<Boolean> {
        favoritesDao.insertFavorite(favorite)
        return Observable.just(true)
    }

    override fun loadFavorites(): Observable<List<Favorite>> = Observable.fromCallable({ favoritesDao.loadAll() })

    override fun deleteFavorite(favorites: Favorite): Observable<Boolean> {
        favoritesDao.deleteFavorite(favorites)
        return Observable.just(true)
    }
}


