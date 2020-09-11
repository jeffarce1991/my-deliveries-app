package com.jeff.deliveries.main.list.presenter

import android.content.Context
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.jeff.deliveries.database.local.Delivery
import com.jeff.deliveries.database.local.Favorite
import com.jeff.deliveries.database.usecase.local.loader.DeliveryLocalLoader
import com.jeff.deliveries.database.usecase.local.loader.FavoriteLocalLoader
import com.jeff.deliveries.main.list.view.MainActivity
import com.jeff.deliveries.webservices.exception.NoInternetException
import com.jeff.deliveries.webservices.internet.RxInternet
import com.jeff.deliveries.main.list.view.MainView
import com.jeff.deliveries.supplychain.deliveries.DeliveriesLoader
import com.jeff.deliveries.utilities.rx.RxSchedulerUtils
import io.reactivex.*
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.lang.NullPointerException
import javax.inject.Inject

class DefaultMainPresenter @Inject
constructor(
    private val internet: RxInternet,
    private val schedulerUtils: RxSchedulerUtils,
    private val loader: DeliveriesLoader,
    private val localLoader: DeliveryLocalLoader,
    private val favoriteLocalLoader: FavoriteLocalLoader
) : MvpBasePresenter<MainView>(),
    MainPresenter {

    lateinit var view: MainView

    lateinit var remoteDisposable: Disposable
    lateinit var localDisposable: Disposable
    lateinit var observerDisposable: Disposable

    override fun loadInitialDeliveries() {
        internet.isConnected()
            .andThen(loader.loadInitial())
            .compose(schedulerUtils.forSingle())
            .subscribe(object : SingleObserver<List<Delivery>>{
                override fun onSuccess(t: List<Delivery>) {
                    Timber.d("==q onSuccess $t" )
                    view.hideProgress()

                    if (t.isNotEmpty()) {
                        generateDeliveryListWithFavorites(t)
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
                        generateDeliveryListWithFavorites(t)
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

    fun loadDeliveriesLocally(favoriteList: List<Favorite>) {
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
                        view.generateDeliveryList(t, favoriteList)
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

    override fun observeFavorites(context: Context) {
        favoriteLocalLoader.observeAll()
            .observe(context as MainActivity, object : Observer<List<Favorite>>,
            androidx.lifecycle.Observer<List<Favorite>> {
            override fun onChanged(t: List<Favorite>?) {
                Timber.d("==q List<Favorite> Changed : ${t!!.size}")
                loadDeliveriesLocally(t)
            }

            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
                Timber.e("==q onSubscribe Favorites Observer")
                observerDisposable = d
            }

            override fun onNext(t: List<Favorite>) {
                Timber.d("==q List<Favorite> Changes : ${t.size}")
            }

            override fun onError(e: Throwable) {
                Timber.e("==q onError Favorites Observer $e")
                dispose(observerDisposable)
            }
        })
    }

    fun generateDeliveryListWithFavorites(deliveryList: List<Delivery>) {
        favoriteLocalLoader.loadAll()
            .compose(schedulerUtils.forSingle())
            .subscribe(object : SingleObserver<List<Favorite>>{
                override fun onSubscribe(d: Disposable) {
                    localDisposable = d
                    Timber.d("==q onSubscribe" )
                }

                override fun onSuccess(t: List<Favorite>) {
                    Timber.d("==q onSuccess" )
                    view.generateDeliveryList(deliveryList, t)
                    dispose(localDisposable)
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                    if (e is NullPointerException) {
                        view.generateDeliveryList(deliveryList)
                    }
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