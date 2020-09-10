package com.jeff.deliveries.main.list.presenter

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.jeff.deliveries.database.local.Delivery
import com.jeff.deliveries.webservices.exception.NoInternetException
import com.jeff.deliveries.webservices.internet.RxInternet
import com.jeff.deliveries.main.list.view.MainView
import com.jeff.deliveries.supplychain.deliveries.DeliveriesLoader
import com.jeff.deliveries.utilities.rx.RxSchedulerUtils
import io.reactivex.*
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

class DefaultMainPresenter @Inject
constructor(
    private val internet: RxInternet,
    private val schedulerUtils: RxSchedulerUtils,
    private val loader: DeliveriesLoader
) : MvpBasePresenter<MainView>(),
    MainPresenter {

    lateinit var view: MainView

    lateinit var remoteDisposable: Disposable
    lateinit var localDisposable: Disposable

    override fun loadInitialDeliveries() {
        internet.isConnected()
            .andThen(loader.loadInitial())
            .compose(schedulerUtils.forSingle())
            .subscribe(object : SingleObserver<List<Delivery>>{
                override fun onSuccess(t: List<Delivery>) {
                    Timber.d("==q onSuccess $t" )
                    view.hideProgress()

                    if (t.isNotEmpty()) {
                        view.generateDeliveryList(t)
                        view.showMessage("${t.size} of Deliveries loaded remotely")
                    } else {
                        view.showMessage("No existing cached data.")
                    }

                    dispose()
                }

                override fun onSubscribe(d: Disposable) {
                    view.showProgress()
                    remoteDisposable = d
                }

                override fun onError(e: Throwable) {
                    Timber.d("==q onError $e" )
                    e.printStackTrace()

                    view.hideProgress()


                    if (e is NoInternetException) {
                        //getPhotosFromLocal()
                    } else {
                        dispose()
                    }
                }
            })
    }

    override fun loadInitialLocally(){
        loader.loadInitialLocally()
            .compose(schedulerUtils.forSingle())
            .subscribe(object : SingleObserver<List<Delivery>>{
                override fun onSubscribe(d: Disposable) {
                    localDisposable = d
                    //view.showProgress()
                }

                override fun onSuccess(t: List<Delivery>) {
                    Timber.d("==q loadInitialLocally onSuccess ${t.size}")

                    //view.hideProgress()

                    if (t.isNotEmpty()) {
                        view.generateDeliveryList(t)
                    } else {
                        view.showMessage("No existing cached data.")
                    }
                    dispose(localDisposable)
                }

                override fun onError(e: Throwable) {
                    Timber.d("==q Load Photos Failed $e")

                    view.showMessage(e.message!!)
                    //view.hideProgress()
                    dispose(localDisposable)

                }
            })
    }


    override fun loadMoreDeliveriesLocally(offset: Int){
        internet.notConnected()
            .andThen(loader.loadMoreDeliveriesLocally(offset))
            .compose(schedulerUtils.forSingle())
            .subscribe(object : SingleObserver<List<Delivery>>{
                override fun onSubscribe(d: Disposable) {
                    localDisposable = d
                    //view.showProgress()
                }

                override fun onSuccess(t: List<Delivery>) {
                    Timber.d("==q loadMoreDeliveriesLocally onSuccess ${t[0].route.end}")

                    //view.hideProgress()

                    if (t.isNotEmpty()) {
                        view.generateDeliveryList(t)
                    } else {
                        view.showMessage("No existing cached data.")
                    }
                    dispose(localDisposable)
                }

                override fun onError(e: Throwable) {
                    Timber.d("==q Load Photos Failed $e")

                    view.showMessage(e.message!!)
                    //view.hideProgress()
                    dispose(localDisposable)

                }
            })
    }

    override fun loadMoreDeliveries(offset: Int) {
        internet.isConnected()
            .andThen(loader.loadMoreDeliveries(offset))
            .compose(schedulerUtils.forSingle())
            .subscribe(object : SingleObserver<List<Delivery>>{
                override fun onSuccess(t: List<Delivery>) {
                    Timber.d("==q onSuccess $t" )
                    view.hideProgress()

                    if (t.isNotEmpty()) {
                        view.generateMoreDeliveries(t)
                        view.showMessage("Data loaded Remotely")
                    } else {
                        view.showMessage("Loading Data Failed")
                    }

                    dispose()
                }

                override fun onSubscribe(d: Disposable) {
                    Timber.d("==q onSubscribe $offset" )
                    view.showProgress()
                    remoteDisposable = d
                }

                override fun onError(e: Throwable) {
                    Timber.d("==q onError $e" )
                    e.printStackTrace()

                    view.hideProgress()


                    if (e is NoInternetException) {
                        //loadMoreDeliveriesLocally(offset)
                    } else {
                        dispose()
                    }
                }
            })
    }

    override fun attachView(view: MainView) {
        super.attachView(view)
        this.view = view
    }

    private fun dispose() {
        if (!remoteDisposable.isDisposed) remoteDisposable.dispose()
    }

    private fun dispose(disposable: Disposable) {
        if (!disposable.isDisposed) disposable.dispose()
    }

    override fun detachView(retainInstance: Boolean) {
        dispose()
    }

}