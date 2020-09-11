package com.jeff.deliveries.database.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = Favorite.TABLE_NAME)
data class Favorite constructor(
                 @ColumnInfo(name = "id")
                 @PrimaryKey var id: String,
                 @ColumnInfo(name = "is_favorite")
                 var isFavorite: Boolean
                ) {

    constructor() : this("-1", false)

    companion object {

        const val COLUMN_ID = "id"
        const val TABLE_NAME = "favorites"
    }
}
