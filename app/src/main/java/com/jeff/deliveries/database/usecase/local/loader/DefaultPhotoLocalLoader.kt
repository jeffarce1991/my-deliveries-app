package com.jeff.deliveries.database.usecase.local.loader

import com.jeff.deliveries.database.local.Delivery
import com.jeff.deliveries.database.room.dao.DeliveryDao
import io.reactivex.Single
import javax.inject.Inject

class DefaultPhotoLocalLoader @Inject
constructor(private val dao: DeliveryDao): PhotoLocalLoader {

    override fun loadAll(): Single<List<Delivery>> {
        return Single.fromCallable { dao.loadAll() }

    }

}