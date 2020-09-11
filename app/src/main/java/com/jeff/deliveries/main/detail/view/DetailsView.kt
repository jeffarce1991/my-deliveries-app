package com.jeff.deliveries.main.detail.view

import com.hannesdorfmann.mosby.mvp.MvpView
import com.jeff.deliveries.database.local.Delivery

interface DetailsView : MvpView {

    fun stopShimmerAnimations()
    fun startShimmerAnimations()
    fun hideShimmerPlaceholders()
    fun showMessage(message: String)

    fun toggleFavoriteButton(isFavorite: Boolean)

    fun setDetails(delivery: Delivery)
}
