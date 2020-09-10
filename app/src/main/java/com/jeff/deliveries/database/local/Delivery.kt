package com.jeff.deliveries.database.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Delivery.TABLE_NAME)
data class Delivery constructor(
                 @ColumnInfo(name = "id")
                 @PrimaryKey var id: String,
                 @ColumnInfo(name = "delivery_fee")
                 var deliveryFee: String,
                 @ColumnInfo(name = "goods_picture")
                 var goodsPicture: String,
                 @ColumnInfo(name = "pickup_time")
                 var pickupTime: String,
                 @ColumnInfo(name = "remarks")
                 var remarks: String,
                 @ColumnInfo(name = "route")
                 var route: Route,
                 @ColumnInfo(name = "sender")
                 var sender: Sender,
                 @ColumnInfo(name = "surcharge")
                 var surcharge: String
) {
    constructor(): this("-1", "", "", "", "", Route(), Sender(), "")

    companion object {

        const val COLUMN_ID = "id"
        const val TABLE_NAME = "deliveries"
    }
}

data class Route(
    var end: String,
    var start: String
) {
    constructor(): this("", "")
}

data class Sender(
    val email: String,
    val name: String,
    val phone: String
) {
    constructor(): this("", "", "")
}
