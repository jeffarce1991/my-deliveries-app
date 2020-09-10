package com.jeff.deliveries.supplychain.photo

import com.jeff.deliveries.database.local.Photo
import io.reactivex.Single

interface PhotoLoader {

    fun loadAll(): Single<List<Photo>>

    fun loadAllFromLocal(): Single<List<Photo>>
}