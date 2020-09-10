package com.jeff.deliveries.supplychain.deliveries

import com.jeff.deliveries.database.local.Delivery
import com.jeff.deliveries.database.usecase.local.loader.PhotoLocalLoader
import com.jeff.deliveries.database.usecase.local.saver.PhotoLocalSaver
import com.jeff.deliveries.main.mapper.DeliveryDtoToDeliveryMapper
import com.jeff.deliveries.webservices.internet.RxInternet
import com.jeff.deliveries.webservices.usecase.loader.PhotoRemoteLoader
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class DefaultDeliveriesLoader @Inject
constructor(private val remoteLoader: PhotoRemoteLoader,
            private val localLoader: PhotoLocalLoader,
            private val localSaver: PhotoLocalSaver,
            private val rxInternet: RxInternet): DeliveriesLoader{

    override fun loadInitial(): Single<List<Delivery>> {
        return rxInternet.isConnected()
            .andThen(remoteLoader.loadInitial())
            .flatMapObservable { list -> Observable.fromIterable(list) }
            .flatMap(DeliveryDtoToDeliveryMapper())
            .toList()
            //.flatMap { photos -> Single.fromObservable(localSaver.saveAll(photos)) }
            .flatMap { photos -> Single.just(photos) }

        //return session.activeAdministeredAccount()
        //.map { administeredAccount -> administeredAccount.tenantId }
        //.flatMap { tenantId -> remoteLoader.loadAvailableExpenses(tenantId) }
    }

    override fun loadAllFromLocal(): Single<List<Delivery>> {
        return rxInternet.notConnected()
            .andThen(localLoader.loadAll())
            /*.flatMap {
                when {
                    it.isEmpty() -> Single.error(
                        Throwable(EmptyResultException()))
                    else -> Single.just(it)
                }
            }*/
            .flatMap { photos -> Single.just(photos)}
    }

}