package com.jeff.deliveries.utilities

import com.jeff.deliveries.utilities.rx.RxSchedulerUtilsModule
import dagger.Module


@Module(includes = [RxSchedulerUtilsModule::class])
abstract class UtilityModule {

}