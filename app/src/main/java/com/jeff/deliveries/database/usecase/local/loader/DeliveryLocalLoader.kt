package com.jeff.deliveries.database.usecase.local.loader

import androidx.lifecycle.LiveData
import com.jeff.deliveries.database.local.Delivery
import io.reactivex.Single

interface DeliveryLocalLoader {
    fun loadInitial(): Single<List<Delivery>>
    fun loadMoreDeliveries(offset: Int): Single<List<Delivery>>
    fun loadById(id: String): Single<Delivery>
    fun observeAll(): LiveData<List<Delivery>>
}