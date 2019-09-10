package com.mindorks.framework.mvp.data.database.repository.questions

import androidx.room.*

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorites")
    fun loadAll(): List<Favorite>

    @Delete
    fun deleteFavorite(favorite: Favorite)
}