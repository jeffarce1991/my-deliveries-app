package com.jeff.deliveries

import android.app.Application
import com.jeff.deliveries.database.DatabaseModule
import com.jeff.deliveries.webservices.internet.RxInternetModule
import com.jeff.deliveries.main.MainModule
import com.jeff.deliveries.supplychain.photo.PhotoUseCaseModule
import com.jeff.deliveries.utilities.UtilityModule
import com.jeff.deliveries.webservices.api.ApiModule
import com.jeff.deliveries.webservices.usecase.WebServiceUseCaseModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = [AndroidSupportInjectionModule::class,
    AndroidSupportInjectionModule::class,
    MainModule::class,
    AppModule::class,
    RxInternetModule::class,
    UtilityModule::class,
    DatabaseModule::class,
    ApiModule::class,
    WebServiceUseCaseModule::class,
    PhotoUseCaseModule::class])
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(myApplication: MyApplication)
}