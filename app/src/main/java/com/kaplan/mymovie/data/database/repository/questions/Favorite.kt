package com.mindorks.framework.mvp.data.database.repository.questions

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorites")
data class Favorite(
        @Expose
        @PrimaryKey
        var id: Long,

        @Expose
        @SerializedName("is_favorite")
        @ColumnInfo(name = "is_favorite")
        var isFavorite: Boolean
)