package com.jeff.deliveries.main.detail.presenter

import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.jeff.deliveries.database.local.Delivery
import com.jeff.deliveries.main.detail.view.DetailsView

interface DetailsPresenter : MvpPresenter<DetailsView> {
    fun saveAsFavorite(id: String, isFavorite: Boolean)
    fun loadFavorite(id: String)
    fun toggleFavorite(id: String)

    fun loadDelivery(id: String)
}
