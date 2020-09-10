package com.jeff.deliveries.database.usecase.local.loader

import com.jeff.deliveries.database.local.Delivery
import io.reactivex.Single

interface PhotoLocalLoader {
    fun loadAll(): Single<List<Delivery>>
}