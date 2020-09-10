package com.jeff.deliveries.webservices.api.deliveries

import com.jeff.deliveries.webservices.dto.DeliveryDto
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DeliveriesApi {

    @GET("v2/deliveries?limit=20&offset=0")
    fun loadDeliveries(): Single<Response<List<DeliveryDto>>>

    @GET("v2/deliveries?limit=20")
    fun loadMoreDeliveries(@Query(value = "offset") id: Int?): Single<Response<List<DeliveryDto>>>

}