package com.jeff.deliveries.database.usecase.local.loader

import androidx.lifecycle.LiveData
import com.jeff.deliveries.database.local.Favorite
import io.reactivex.Single

interface FavoriteLocalLoader {
    fun loadAll(): Single<List<Favorite>>
    fun observeAll(): LiveData<List<Favorite>>
    fun loadById(id: String): Single<Favorite>
}