package com.jeff.deliveries.main.list.presenter

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.jeff.deliveries.database.local.Delivery
import com.jeff.deliveries.database.usecase.local.loader.PhotoLocalLoader
import com.jeff.deliveries.database.usecase.local.saver.PhotoLocalSaver
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
    private val localLoader: PhotoLocalLoader,
    private val localSaver: PhotoLocalSaver,
    private val schedulerUtils: RxSchedulerUtils,
    private val loader: DeliveriesLoader
) : MvpBasePresenter<MainView>(),
    MainPresenter {

    lateinit var view: MainView

    lateinit var disposable: Disposable

    override fun loadInitialDeliveries() {
        internet.isConnected()
            .andThen(loader.loadInitial())
            .compose(schedulerUtils.forSingle())
            .subscribe(object : SingleObserver<List<Delivery>>{
                override fun onSuccess(t: List<Delivery>) {
                    Timber.d("==q onError $t" )
                    view.hideProgress()
                    if (t.isNotEmpty()) {
                        view.generateDataList(t)
                        view.showToast("Data loaded Remotely")
                    } else {
                        view.showLoadingDataFailed()
                    }
                    dispose()
                }

                override fun onSubscribe(d: Disposable) {
                    view.showProgress()
                    disposable = d
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

    fun getPhotosFromLocal(){
        loader.loadAllFromLocal()
            .compose(schedulerUtils.forSingle())
            .subscribe(object : SingleObserver<List<Delivery>>{
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                    view.showProgress()
                }

                override fun onSuccess(t: List<Delivery>) {
                    Timber.d("==q loadAll onSuccess ${t.size}")

                    view.hideProgress()

                    if (t.isNotEmpty()) {
                        view.showToast("Data loaded Locally")
                        view.generateDataList(t)
                    } else {
                        view.showLoadingDataFailed()
                    }
                    dispose()
                }

                override fun onError(e: Throwable) {
                    Timber.d("==q Load Photos Failed $e")

                    view.hideProgress()
                    dispose()

                }
            })
    }

    override fun attachView(view: MainView) {
        super.attachView(view)
        this.view = view
    }

    private fun dispose() {
        if (!disposable.isDisposed) disposable.dispose()
    }

    override fun detachView(retainInstance: Boolean) {
        dispose()
    }

}