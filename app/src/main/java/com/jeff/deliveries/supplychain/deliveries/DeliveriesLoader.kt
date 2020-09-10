package com.jeff.deliveries.supplychain.deliveries

import com.jeff.deliveries.database.local.Delivery
import io.reactivex.Single

interface DeliveriesLoader {

    fun loadInitial(): Single<List<Delivery>>
    fun loadMoreDeliveries(offset: Int): Single<List<Delivery>>

    fun loadInitialLocally(): Single<List<Delivery>>
    fun loadMoreDeliveriesLocally(offset: Int): Single<List<Delivery>>
}