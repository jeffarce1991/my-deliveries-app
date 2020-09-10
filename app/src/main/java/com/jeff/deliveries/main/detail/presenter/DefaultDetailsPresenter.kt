package com.jeff.deliveries.main.detail.presenter

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.jeff.deliveries.main.detail.view.DetailsView
import com.jeff.deliveries.utilities.rx.RxSchedulerUtils
import com.jeff.deliveries.webservices.internet.RxInternet
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class DefaultDetailsPresenter @Inject
constructor(
    private val rxInternet: RxInternet,
    private val rxScheduler: RxSchedulerUtils
) : MvpBasePresenter<DetailsView>(),
    DetailsPresenter {

    lateinit var view: DetailsView

    lateinit var disposable: Disposable

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