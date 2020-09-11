package com.jeff.deliveries.database.usecase.local.saver

import com.jeff.deliveries.database.local.Delivery
import com.jeff.deliveries.database.local.Favorite
import io.reactivex.Completable
import io.reactivex.Observable

interface FavoriteLocalSaver {

    fun save(favorite: Favorite): Completable

    fun saveAll(favorites: List<Favorite>): Observable<List<Favorite>>
}
