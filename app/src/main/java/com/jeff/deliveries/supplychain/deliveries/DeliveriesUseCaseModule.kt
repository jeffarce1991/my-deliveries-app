package com.jeff.deliveries.supplychain.deliveries

import dagger.Binds
import dagger.Module

@Module
abstract class DeliveriesUseCaseModule {

    @Binds
    abstract fun bindPhotoLoader(defaultDeliveriesLoader: DefaultDeliveriesLoader): DeliveriesLoader
}