package com.jeff.deliveries.database.usecase.local.loader

import com.jeff.deliveries.database.local.Photo
import io.reactivex.Single

interface PhotoLocalLoader {
    fun loadAll(): Single<List<Photo>>
}