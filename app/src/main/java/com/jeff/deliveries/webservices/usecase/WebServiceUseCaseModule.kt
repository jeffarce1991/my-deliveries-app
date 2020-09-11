package com.jeff.deliveries.webservices.usecase

import com.jeff.deliveries.webservices.usecase.loader.DefaultDeliveriesRemoteLoader
import com.jeff.deliveries.webservices.usecase.loader.DeliveriesRemoteLoader
import dagger.Binds
import dagger.Module

@Module
interface WebServiceUseCaseModule {

    @Binds
    fun bindPhotoRemoteLoader(
            defaultPhotoRemoteLoader: DefaultDeliveriesRemoteLoader):
            DeliveriesRemoteLoader
}