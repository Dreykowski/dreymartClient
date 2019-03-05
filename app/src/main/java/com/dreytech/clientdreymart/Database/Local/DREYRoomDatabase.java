package com.dreytech.clientdreymart.Database.Local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.dreytech.clientdreymart.Database.ModelDB.Cart;
import com.dreytech.clientdreymart.Database.ModelDB.Favorite;

@Database(entities = {Cart.class, Favorite.class}, version = 1, exportSchema = false)
public abstract class DREYRoomDatabase extends RoomDatabase {

    public abstract CartDAO cartDAO();
    public abstract FavoriteDAO favoriteDAO();

    private static DREYRoomDatabase instance;

    public static DREYRoomDatabase getInstance(Context context)
    {
        if (instance == null)
            instance = Room.databaseBuilder(context,DREYRoomDatabase.class,"DREY_DreymartDB")
                    .allowMainThreadQueries()
                    .build();
        return instance;
    }

}
