package com.jeff.deliveries.main.list.view

import com.hannesdorfmann.mosby.mvp.MvpView
import com.jeff.deliveries.database.local.Delivery
import com.jeff.deliveries.database.local.Favorite

interface MainView : MvpView {
     fun hideProgress()
     fun showProgress()
     fun showMessage(message: String)

     fun generateDeliveryList(deliveries: List<Delivery>)
     fun generateDeliveryList(deliveries: List<Delivery>, favoriteList: List<Favorite>)
     fun generateMoreDeliveries(deliveries: List<Delivery>)

}