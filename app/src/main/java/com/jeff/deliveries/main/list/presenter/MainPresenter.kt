package com.jeff.deliveries.main.list.presenter

import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.jeff.deliveries.main.list.view.MainView

interface MainPresenter: MvpPresenter<MainView> {
    fun loadInitialDeliveries()
    fun loadInitialLocally()
    fun loadMoreDeliveries(offset: Int)
    fun loadMoreDeliveriesLocally(offset: Int)
}