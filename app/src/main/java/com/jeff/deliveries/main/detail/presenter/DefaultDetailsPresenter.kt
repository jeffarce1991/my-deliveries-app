package com.jeff.deliveries.main.detail.presenter

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.jeff.deliveries.database.local.Delivery
import com.jeff.deliveries.database.local.Favorite
import com.jeff.deliveries.database.usecase.local.loader.DeliveryLocalLoader
import com.jeff.deliveries.database.usecase.local.loader.FavoriteLocalLoader
import com.jeff.deliveries.database.usecase.local.saver.DeliveryLocalSaver
import com.jeff.deliveries.database.usecase.local.saver.FavoriteLocalSaver
import com.jeff.deliveries.main.detail.view.DetailsView
import com.jeff.deliveries.utilities.rx.RxSchedulerUtils
import com.jeff.deliveries.webservices.internet.RxInternet
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DefaultDetailsPresenter @Inject
constructor(
    private val rxInternet: RxInternet,
    private val rxScheduler: RxSchedulerUtils,
    private val favoriteSaver: FavoriteLocalSaver,
    private val favoriteLoader: FavoriteLocalLoader,
    private val loader: DeliveryLocalLoader,
    private val saver: DeliveryLocalSaver

    ) : MvpBasePresenter<DetailsView>(),
    DetailsPresenter {

    lateinit var view: DetailsView

    lateinit var disposable: Disposable

    override fun saveAsFavorite(id: String, isFavorite: Boolean) {
        favoriteSaver.save(Favorite(id, isFavorite))
            .compose(rxScheduler.forCompletable())
            .subscribe(object : CompletableObserver{
                override fun onComplete() {
                    dispose()
                }

                override fun onSubscribe(d: Disposable) {
                    Timber.d("==q Favorite saving.")
                    disposable = d
                }

                override fun onError(e: Throwable) {
                    Timber.d("==q Favorite saving failed.")
                    dispose()
                }
            })
    }

    override fun loadFavorite(id: String) {
        favoriteLoader.loadById(id)
            .compose(rxScheduler.forSingle())
            .subscribe(object : SingleObserver<Favorite>{
                override fun onSubscribe(d: Disposable) {
                    Timber.d("==q onSubscribe")
                }

                override fun onSuccess(t: Favorite) {
                    Timber.d("==q Loaded Favorite $t")
                    view.toggleFavoriteButton(t.isFavorite)
                }

                override fun onError(e: Throwable) {
                    Timber.d("==q Error Loading Favorite $e")
                    if (e is NullPointerException) {
                    } else {
                        view.showMessage(e.message!!)
                    }
                }
            })
    }

    override fun toggleFavorite(id: String) {
        favoriteLoader.loadById(id)
            .compose(rxScheduler.forSingle())
            .subscribe(object : SingleObserver<Favorite>{
                override fun onSubscribe(d: Disposable) {
                    Timber.d("==q onSubscribe")
                }

                override fun onSuccess(t: Favorite) {
                    Timber.d("==q Loaded Favorite $t")
                    saveAsFavorite(t.id, !t.isFavorite)
                    view.toggleFavoriteButton(!t.isFavorite)
                }

                override fun onError(e: Throwable) {
                    Timber.d("==q Error Loading Favorite $e")

                    if (e is NullPointerException) {
                        saveAsFavorite(id, true)
                        view.toggleFavoriteButton(true)
                    }
                }
            })
    }

    override fun loadDelivery(id: String) {
        loader.loadById(id)
            .delay(1,TimeUnit.SECONDS)
            .compose(rxScheduler.forSingle())
            .subscribe(object : SingleObserver<Delivery>{
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                    view.startShimmerAnimations()
                }

                override fun onSuccess(t: Delivery) {
                    Timber.d("==q Loaded Delivery $t")
                    view.setDetails(t)
                    view.stopShimmerAnimations()
                    view.hideShimmerPlaceholders()
                }

                override fun onError(e: Throwable) {
                    Timber.e("==q onError $e")
                    view.stopShimmerAnimations()
                    view.hideShimmerPlaceholders()
                }
            })
    }

    override fun attachView(view: DetailsView) {
        super.attachView(view)
        this.view = view
    }

    private fun dispose() {
        if (!disposable.isDisposed) disposable.dispose()
    }

    override fun detachView(retainInstance: Boolean) {
        //dispose()
    }
}