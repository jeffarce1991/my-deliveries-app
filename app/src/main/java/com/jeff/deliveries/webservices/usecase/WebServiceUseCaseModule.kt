package com.jeff.deliveries.webservices.usecase

import com.jeff.deliveries.webservices.usecase.loader.DefaultPhotoRemoteLoader
import com.jeff.deliveries.webservices.usecase.loader.PhotoRemoteLoader
import dagger.Binds
import dagger.Module

@Module
interface WebServiceUseCaseModule {

    @Binds
    fun bindPhotoRemoteLoader(
            defaultPhotoRemoteLoader: DefaultPhotoRemoteLoader):
            PhotoRemoteLoader
}