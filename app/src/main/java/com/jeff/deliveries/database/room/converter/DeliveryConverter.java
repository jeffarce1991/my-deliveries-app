package com.jeff.deliveries.database.room.converter;

import androidx.room.TypeConverter;

import com.jeff.deliveries.database.local.Delivery;
import com.jeff.deliveries.database.local.Route;
import com.jeff.deliveries.database.local.Sender;
import com.jeff.deliveries.utilities.ConverterUtil;

public class DeliveryConverter {
    private DeliveryConverter() { }

    @TypeConverter
    public static String fromDelivery(Delivery delivery) {
        return ConverterUtil.serialise(delivery);
    }

    @TypeConverter
    public static Delivery toDelivery(String serialised) {
        return ConverterUtil.deserialise(serialised, Delivery.class);
    }

    @TypeConverter
    public static String fromRoute(Route route) {
        return ConverterUtil.serialise(route);
    }

    @TypeConverter
    public static Route toRoute(String serialised) {
        return ConverterUtil.deserialise(serialised, Route.class);
    }

    @TypeConverter
    public static String fromSender(Sender sender) {
        return ConverterUtil.serialise(sender);
    }

    @TypeConverter
    public static Sender toSender(String serialised) {
        return ConverterUtil.deserialise(serialised, Sender.class);
    }


}
