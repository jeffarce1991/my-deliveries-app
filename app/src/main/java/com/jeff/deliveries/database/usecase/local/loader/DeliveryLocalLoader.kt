package com.jeff.deliveries.database.usecase.local.loader

import com.jeff.deliveries.database.local.Delivery
import io.reactivex.Single

interface DeliveryLocalLoader {
    fun loadInitial(): Single<List<Delivery>>
    fun loadMoreDeliveries(offset: Int): Single<List<Delivery>>
}