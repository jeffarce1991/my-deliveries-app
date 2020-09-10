package com.jeff.deliveries.webservices.usecase.loader

import com.jeff.deliveries.webservices.dto.DeliveryDto
import io.reactivex.Single

interface DeliveriesRemoteLoader {

    fun loadInitial(): Single<List<DeliveryDto>>
    fun loadMoreDeliveries(offset: Int): Single<List<DeliveryDto>>
}