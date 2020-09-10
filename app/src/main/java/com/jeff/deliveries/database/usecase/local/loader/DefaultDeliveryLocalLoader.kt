package com.jeff.deliveries.database.usecase.local.loader

import com.jeff.deliveries.database.local.Delivery
import com.jeff.deliveries.database.room.dao.DeliveryDao
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

}