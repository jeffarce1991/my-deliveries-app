package com.jeff.deliveries.database.usecase.local.saver

import com.jeff.deliveries.database.local.Delivery
import com.jeff.deliveries.database.local.Favorite
import com.jeff.deliveries.database.room.dao.DeliveryDao
import com.jeff.deliveries.database.room.dao.FavoritesDao
import io.reactivex.Completable
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class DefaultFavoriteLocalSaver @Inject
constructor(private val dao: FavoritesDao) : FavoriteLocalSaver {

    override fun save(favorite: Favorite): Completable {
        return Completable.fromAction { dao.insert(favorite)}
    }

    override fun saveAll(favorites: List<Favorite>): Observable<List<Favorite>> {
        return Completable.fromCallable {
            dao.insert(favorites)
            Timber.d("==q saveAll Done")
            Completable.complete()
        }.andThen(Observable.fromCallable { favorites })
    }

}