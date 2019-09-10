package com.mindorks.framework.mvp.data.database.repository.questions

import io.reactivex.Observable

interface FavoriteRepo {

    fun isFavoritesRepoEmpty(): Observable<Boolean>

    fun insertFavorite(favorites: Favorite): Observable<Boolean>

    fun loadFavorites(): Observable<List<Favorite>>

    fun deleteFavorite(favorites: Favorite) : Observable<Boolean>
}