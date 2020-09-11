package com.jeff.deliveries.database.usecase.local.loader

import androidx.lifecycle.LiveData
import com.jeff.deliveries.database.local.Delivery
import com.jeff.deliveries.database.room.dao.DeliveryDao
import com.jeff.deliveries.database.room.dao.FavoritesDao
import io.reactivex.Single
import javax.inject.Inject

class DefaultDeliveryLocalLoader @Inject
constructor(private val dao: DeliveryDao): DeliveryLocalLoader {

    override fun loadInitial(): Single<List<Delivery>> {
        return Single.fromCallable { dao.loadAll() }
    }

    override fun loadMoreDeliveries(offset: Int): Single<List<Delivery>> {
        return Single.fromCallable { dao.loadMoreDeliveries(offset) }
    }

    override fun loadById(id: String): Single<Delivery> {
        return Single.fromCallable { dao.loadById(id) }
    }

    override fun observeAll(): LiveData<List<Delivery>> {
        return dao.observeAll()
    }


}