package com.kaplan.mymovie.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mindorks.framework.mvp.data.database.repository.questions.Favorite
import com.mindorks.framework.mvp.data.database.repository.questions.FavoritesDao

@Database(entities = [(Favorite::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoritesDao(): FavoritesDao

    //test

}