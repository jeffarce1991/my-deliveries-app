package com.jeff.deliveries.webservices.dto

class DeliveryDto(
    var id: String,
    var deliveryFee: String,
    var goodsPicture: String,
    var pickupTime: String,
    var remarks: String,
    var route: RouteDto,
    var sender: SenderDto,
    var surcharge: String
) {
}
data class RouteDto(
    var end: String,
    var start: String
)

data class SenderDto(
    val email: String,
    val name: String,
    val phone: String
)