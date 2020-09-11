package com.jeff.deliveries.database.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jeff.deliveries.database.local.Favorite

@Dao
interface FavoritesDao {

    @Query("Select * FROM " + Favorite.TABLE_NAME)
    fun loadAll(): List<Favorite>

    @Query("Select * FROM " + Favorite.TABLE_NAME)
    fun observeAll(): LiveData<List<Favorite>>

    @Query(
        "SELECT * FROM " + Favorite.TABLE_NAME +
                " WHERE " + Favorite.COLUMN_ID + " LIKE :id"
    )
    fun loadById(id: String): Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: Favorite)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorites: List<Favorite>)
}