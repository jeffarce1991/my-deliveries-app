package com.jeff.deliveries.main

import com.jeff.deliveries.ActivityScope
import com.jeff.deliveries.main.detail.presenter.DetailsPresenterModule
import com.jeff.deliveries.main.detail.view.DetailsActivity
import com.jeff.deliveries.main.list.presenter.MainPresenterModule
import com.jeff.deliveries.main.list.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainPresenterModule::class])
    fun mainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [DetailsPresenterModule::class])
    fun detailsActivity(): DetailsActivity
}