package com.ronx.project.findyourbus.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface SearchDao {

    @Query("SELECT * FROM search ORDER BY updated_at")
    LiveData<List<SearchEntry>> loadAllSearches();

    @Insert
    void insertSearch(SearchEntry searchEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateSearch(SearchEntry searchEntry);

    @Delete
    void deleteSearch(SearchEntry searchEntry);

    @Query("SELECT * FROM search WHERE id = :id")
    LiveData<SearchEntry> loadSearchById(int id);
}
