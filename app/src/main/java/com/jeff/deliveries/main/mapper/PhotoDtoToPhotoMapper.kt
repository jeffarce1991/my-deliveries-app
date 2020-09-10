package com.jeff.deliveries.main.mapper

import com.jeff.deliveries.database.local.Photo
import com.jeff.deliveries.webservices.dto.PhotoDto
import io.reactivex.Observable
import io.reactivex.functions.Function

class PhotoDtoToPhotoMapper : Function<PhotoDto, Observable<Photo>> {

    @Throws(Exception::class)
    override fun apply(photoDto: PhotoDto): Observable<Photo> {
        return Observable.fromCallable {
            val photo = Photo(
                photoDto.id,
                photoDto.albumId,
                photoDto.title,
                photoDto.url,
                photoDto.thumbnailUrl
            )
            photo
        }
    }
}