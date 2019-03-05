package com.dreytech.clientdreymart.Database.DataSource;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Query;

import com.dreytech.clientdreymart.Database.ModelDB.Favorite;

import java.util.List;

import io.reactivex.Flowable;

public interface IFavoriteDataSource {

    Flowable<List<Favorite>> getFavItems();


    int isFavorite(int itemId);

    void insertFav(Favorite...favorites);

    void delete(Favorite favorite);
}
