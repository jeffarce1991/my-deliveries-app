package com.jeff.deliveries.supplychain.deliveries

import com.jeff.deliveries.database.local.Delivery
import com.jeff.deliveries.database.usecase.local.loader.DeliveryLocalLoader
import com.jeff.deliveries.database.usecase.local.saver.PhotoLocalSaver
import com.jeff.deliveries.main.mapper.DeliveryDtoToDeliveryMapper
import com.jeff.deliveries.webservices.internet.RxInternet
import com.jeff.deliveries.webservices.usecase.loader.DeliveriesRemoteLoader
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class DefaultDeliveriesLoader @Inject
constructor(private val remoteLoader: DeliveriesRemoteLoader,
            private val localLoader: DeliveryLocalLoader,
            private val localSaver: PhotoLocalSaver,
            private val rxInternet: RxInternet): DeliveriesLoader{

    override fun loadInitial(): Single<List<Delivery>> {
        return rxInternet.isConnected()
            .andThen(remoteLoader.loadInitial())
            .flatMapObservable { list -> Observable.fromIterable(list) }
            .flatMap(DeliveryDtoToDeliveryMapper())
            .toList()
            .flatMap { Single.fromObservable(localSaver.saveAll(it)) }
            .flatMap { Single.just(it) }

        //return session.activeAdministeredAccount()
        //.map { administeredAccount -> administeredAccount.tenantId }
        //.flatMap { tenantId -> remoteLoader.loadAvailableExpenses(tenantId) }
    }

    override fun loadMoreDeliveries(offset: Int): Single<List<Delivery>> {
        return rxInternet.isConnected()
            .andThen(remoteLoader.loadMoreDeliveries(offset))
            .flatMapObservable { list -> Observable.fromIterable(list) }
            .flatMap(DeliveryDtoToDeliveryMapper())
            .toList()
            .flatMap { Single.fromObservable(localSaver.saveAll(it)) }
            .flatMap { Single.just(it) }

        //return session.activeAdministeredAccount()
        //.map { administeredAccount -> administeredAccount.tenantId }
        //.flatMap { tenantId -> remoteLoader.loadAvailableExpenses(tenantId) }
    }

    override fun loadInitialLocally(): Single<List<Delivery>> {
        return localLoader.loadInitial()
            .flatMap { photos -> Single.just(photos)}
    }

    override fun loadMoreDeliveriesLocally(offset: Int): Single<List<Delivery>> {
        return localLoader.loadMoreDeliveries(offset)
            .flatMap { photos -> Single.just(photos)}
    }

}