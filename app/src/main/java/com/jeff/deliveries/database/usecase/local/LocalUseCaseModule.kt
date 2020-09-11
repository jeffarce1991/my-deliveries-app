package com.jeff.deliveries.database.usecase.local

import com.jeff.deliveries.database.usecase.local.loader.DefaultDeliveryLocalLoader
import com.jeff.deliveries.database.usecase.local.loader.DefaultFavoriteLocalLoader
import com.jeff.deliveries.database.usecase.local.loader.DeliveryLocalLoader
import com.jeff.deliveries.database.usecase.local.loader.FavoriteLocalLoader
import com.jeff.deliveries.database.usecase.local.saver.DefaultDeliveryLocalSaver
import com.jeff.deliveries.database.usecase.local.saver.DefaultFavoriteLocalSaver
import com.jeff.deliveries.database.usecase.local.saver.DeliveryLocalSaver
import com.jeff.deliveries.database.usecase.local.saver.FavoriteLocalSaver
import dagger.Binds
import dagger.Module

@Module
interface LocalUseCaseModule {
    @Binds
    fun bindPhotoLocalLoader(implementation: DefaultDeliveryLocalLoader): DeliveryLocalLoader

    @Binds
    fun bindPhotoLocalSaver(implementation: DefaultDeliveryLocalSaver): DeliveryLocalSaver

    @Binds
    fun bindFavoriteLocalLoader(implementation: DefaultFavoriteLocalLoader): FavoriteLocalLoader

    @Binds
    fun bindFavoriteLocalSaver(implementation: DefaultFavoriteLocalSaver): FavoriteLocalSaver
}