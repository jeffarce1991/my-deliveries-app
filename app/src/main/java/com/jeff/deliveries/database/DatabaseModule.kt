package com.jeff.deliveries.database

import android.app.Application
import androidx.room.Room
import com.jeff.deliveries.R
import com.jeff.deliveries.database.room.AppDatabase
import com.jeff.deliveries.database.room.dao.DeliveryDao
import com.jeff.deliveries.database.usecase.local.LocalUseCaseModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [LocalUseCaseModule::class])
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application,
            AppDatabase::class.java,
            application.getString(R.string.db_name))
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideAssignmentDao(appDatabase: AppDatabase): DeliveryDao {
        return appDatabase.deliveryDao()
    }
}