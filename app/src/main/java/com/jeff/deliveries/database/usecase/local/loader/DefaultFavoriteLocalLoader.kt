package com.jeff.deliveries.database.usecase.local.loader

import androidx.lifecycle.LiveData
import com.jeff.deliveries.database.local.Favorite
import com.jeff.deliveries.database.room.dao.FavoritesDao
import io.reactivex.Single
import javax.inject.Inject

class DefaultFavoriteLocalLoader @Inject
constructor(private val dao: FavoritesDao): FavoriteLocalLoader {


    override fun loadAll(): Single<List<Favorite>> {
        return Single.fromCallable { dao.loadAll() }
    }

    override fun observeAll(): LiveData<List<Favorite>> {
        return dao.observeAll()
    }

    override fun loadById(id: String): Single<Favorite> {
        return Single.fromCallable { dao.loadById(id) }
    }


}