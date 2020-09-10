package com.jeff.deliveries.database.usecase.local.saver

import com.jeff.deliveries.database.local.Delivery
import io.reactivex.Completable
import io.reactivex.Observable

interface PhotoLocalSaver {

    fun save(delivery: Delivery): Completable

    fun saveAll(deliveries: List<Delivery>): Observable<List<Delivery>>
}
