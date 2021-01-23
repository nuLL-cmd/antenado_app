package com.automatodev.antenado.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.automatodev.antenado.models.TvMostPopular;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface TvShowDao {

    @Query("SELECT * FROM tvShows")
    Flowable<List<TvMostPopular>> getFavourites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addFavourites(TvMostPopular tvMostPopular);

    @Delete
    void removeFovourite(TvMostPopular tvMostPopular);
}
