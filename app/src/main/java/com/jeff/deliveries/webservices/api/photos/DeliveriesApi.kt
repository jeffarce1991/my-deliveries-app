package com.jeff.deliveries.webservices.api.photos

import com.jeff.deliveries.webservices.dto.DeliveryDto
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface DeliveriesApi {

    @GET("v2/deliveries?limit=20&offset=0")
    fun loadDeliveries(): Single<Response<List<DeliveryDto>>>

}