package com.jeff.deliveries.main.list.view

import com.hannesdorfmann.mosby.mvp.MvpView
import com.jeff.deliveries.database.local.Delivery

interface MainView : MvpView {
     fun hideProgress()
     fun showProgress()
     fun showProgressRemote()
     fun showProgressLocal()

     fun showLoadingDataFailed()
     fun showToast(message: String)
     fun generateDataList(deliveries: List<Delivery>)
}