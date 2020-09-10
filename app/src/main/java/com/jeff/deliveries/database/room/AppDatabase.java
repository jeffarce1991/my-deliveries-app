package com.jeff.deliveries.database.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.jeff.deliveries.database.local.Photo;
import com.jeff.deliveries.database.room.converter.PhotoConverter;
import com.jeff.deliveries.database.room.dao.PhotoDao;

@Database(
        entities = {
                Photo.class
        },
        version = 3,
        exportSchema = false
)

@TypeConverters(
        {
                PhotoConverter.class
        })
public abstract class AppDatabase extends RoomDatabase {
        public abstract PhotoDao photoDao();
}
