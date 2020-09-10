package com.jeff.deliveries.main.mapper

import com.jeff.deliveries.database.local.Delivery
import com.jeff.deliveries.database.local.Route
import com.jeff.deliveries.database.local.Sender
import com.jeff.deliveries.webservices.dto.DeliveryDto
import io.reactivex.Observable
import io.reactivex.functions.Function

class DeliveryDtoToDeliveryMapper : Function<DeliveryDto, Observable<Delivery>> {

    @Throws(Exception::class)
    override fun apply(dto: DeliveryDto): Observable<Delivery> {
        return Observable.fromCallable {
            val photo = Delivery(
                dto.id,
                dto.deliveryFee,
                dto.goodsPicture,
                dto.pickupTime,
                dto.remarks,
                Route(dto.route.end, dto.route.start),
                Sender(dto.sender.email, dto.sender.name, dto.sender.phone),
                dto.surcharge
            )
            photo
        }
    }
}