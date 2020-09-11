package com.jeff.deliveries.database.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.jeff.deliveries.database.local.Delivery;
import com.jeff.deliveries.database.local.Favorite;
import com.jeff.deliveries.database.room.converter.DeliveryConverter;
import com.jeff.deliveries.database.room.dao.DeliveryDao;
import com.jeff.deliveries.database.room.dao.FavoritesDao;

@Database(
        entities = {
                Delivery.class,
                Favorite.class,
        },
        version = 10,
        exportSchema = false
)

@TypeConverters(
        {
                DeliveryConverter.class
        })
public abstract class AppDatabase extends RoomDatabase {
        public abstract DeliveryDao deliveryDao();
        public abstract FavoritesDao favoritesDao();
}
