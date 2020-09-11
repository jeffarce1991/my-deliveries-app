package com.jeff.deliveries.database.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jeff.deliveries.database.local.Delivery
import com.jeff.deliveries.database.local.Favorite

@Dao
interface DeliveryDao {
    @Query("Select * FROM " + Delivery.TABLE_NAME)
    fun loadAll(): List<Delivery>

    @Query("Select * FROM " + Delivery.TABLE_NAME)
    fun observeAll(): LiveData<List<Delivery>>

    @Query("Select * FROM " + Delivery.TABLE_NAME +
            " WHERE "+ Delivery.COLUMN_ID +" IN (:id)")
    fun loadAllByIds(id: IntArray): List<Delivery>

   /* @Query("SELECT * FROM " + Delivery.TABLE_NAME +
            " WHERE title LIKE :title AND title LIMIT 1")
    fun findByTitle(title: String): Delivery*/

    @Query(
        "SELECT * FROM " + Delivery.TABLE_NAME +
                " WHERE " + Delivery.COLUMN_ID + " LIKE :id"
    )
    fun loadById(id: String): Delivery

    @Query("Select * FROM " + Delivery.TABLE_NAME + " LIMIT 20 OFFSET :offset")
    fun loadMoreDeliveries(offset: Int): List<Delivery>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(deliveries: List<Delivery>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(delivery: Delivery)

    @Delete
    fun delete(delivery: Delivery)

    @Update
    fun update(delivery: Delivery)

}