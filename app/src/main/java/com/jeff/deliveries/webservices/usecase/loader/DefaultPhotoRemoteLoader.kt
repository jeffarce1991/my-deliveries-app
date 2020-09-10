package com.jeff.deliveries.webservices.usecase.loader

import com.jeff.deliveries.webservices.api.ApiFactory
import com.jeff.deliveries.webservices.api.photos.DeliveriesApi
import com.jeff.deliveries.webservices.dto.DeliveryDto
import com.jeff.deliveries.webservices.transformer.ResponseCodeNot200SingleTransformer
import io.reactivex.Single
import javax.inject.Inject

class DefaultPhotoRemoteLoader @Inject
constructor(private val apiFactory: ApiFactory): PhotoRemoteLoader {

    override fun loadInitial(): Single<List<DeliveryDto>> {
        return apiFactory.create(DeliveriesApi::class.java)
            .flatMap { it.loadDeliveries() }
            .compose(ResponseCodeNot200SingleTransformer())
            .flatMap { response ->
                Single.just(response.body()!!) }
    }
}