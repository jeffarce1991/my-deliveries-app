package com.jeff.deliveries.webservices.usecase.loader

import com.jeff.deliveries.webservices.dto.DeliveryDto
import io.reactivex.Single

interface PhotoRemoteLoader {

    fun loadInitial(): Single<List<DeliveryDto>>
}