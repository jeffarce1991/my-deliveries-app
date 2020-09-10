package com.jeff.deliveries.main.detail.presenter

import dagger.Binds
import dagger.Module

@Module
abstract class DetailsPresenterModule {

    @Binds
    abstract fun bindUserDetailsPresenter(
        defaultUserDetailsPresenter: DefaultDetailsPresenter
    ): DetailsPresenter
}