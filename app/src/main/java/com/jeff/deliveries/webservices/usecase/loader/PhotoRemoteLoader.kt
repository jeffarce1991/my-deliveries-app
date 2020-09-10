package com.jeff.deliveries.webservices.usecase.loader

import com.jeff.deliveries.webservices.dto.PhotoDto
import io.reactivex.Single

interface PhotoRemoteLoader {

    fun loadAll(): Single<List<PhotoDto>>
}