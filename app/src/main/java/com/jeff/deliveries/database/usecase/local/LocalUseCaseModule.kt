package com.jeff.deliveries.database.usecase.local

import com.jeff.deliveries.database.usecase.local.loader.DefaultDeliveryLocalLoader
import com.jeff.deliveries.database.usecase.local.loader.DeliveryLocalLoader
import com.jeff.deliveries.database.usecase.local.saver.DefaultPhotoLocalSaver
import com.jeff.deliveries.database.usecase.local.saver.PhotoLocalSaver
import dagger.Binds
import dagger.Module

@Module
interface LocalUseCaseModule {
    @Binds
    fun bindPhotoLocalLoader(implementation: DefaultDeliveryLocalLoader): DeliveryLocalLoader

    @Binds
    fun bindPhotoLocalSaver(implementation: DefaultPhotoLocalSaver): PhotoLocalSaver
}