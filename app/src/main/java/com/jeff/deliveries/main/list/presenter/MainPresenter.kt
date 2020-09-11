package com.jeff.deliveries.main.list.presenter

import android.content.Context
import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.jeff.deliveries.main.list.view.MainView

interface MainPresenter: MvpPresenter<MainView> {
    fun loadInitialDeliveries()
    fun loadInitialLocally()
    fun loadMoreDeliveries(offset: Int)
    fun loadMoreDeliveriesLocally(offset: Int)
    fun observeFavorites(context: Context)
}