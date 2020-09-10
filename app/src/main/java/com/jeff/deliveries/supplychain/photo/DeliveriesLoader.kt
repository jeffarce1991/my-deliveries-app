package com.jeff.deliveries.supplychain.photo

import com.jeff.deliveries.database.local.Delivery
import io.reactivex.Single

interface DeliveriesLoader {

    fun loadInitial(): Single<List<Delivery>>

    fun loadAllFromLocal(): Single<List<Delivery>>
}