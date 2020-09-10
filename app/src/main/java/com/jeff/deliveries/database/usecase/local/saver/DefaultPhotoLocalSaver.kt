package com.jeff.deliveries.database.usecase.local.saver

import com.jeff.deliveries.database.local.Delivery
import com.jeff.deliveries.database.room.dao.DeliveryDao
import io.reactivex.Completable
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class DefaultPhotoLocalSaver @Inject
constructor(private val dao: DeliveryDao) : PhotoLocalSaver {

    override fun save(delivery: Delivery): Completable {
        return Completable.fromAction { dao.insert(delivery)}
    }

    override fun saveAll(deliveries: List<Delivery>): Observable<List<Delivery>> {
        return Completable.fromCallable {
            dao.insert(deliveries)
            Timber.d("==q saveAll Done")
            Completable.complete()
        }.andThen(Observable.fromCallable { deliveries })
    }

}