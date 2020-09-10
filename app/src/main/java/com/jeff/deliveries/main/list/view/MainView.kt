package com.jeff.deliveries.main.list.view

import com.hannesdorfmann.mosby.mvp.MvpView
import com.jeff.deliveries.database.local.Delivery

interface MainView : MvpView {
     fun hideProgress()
     fun showProgress()
     fun showMessage(message: String)

     fun generateDeliveryList(deliveries: List<Delivery>)
     fun generateMoreDeliveries(deliveries: List<Delivery>)

}