package com.ronx.project.findyourbus.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.ronx.project.findyourbus.database.AppDatabase;
import com.ronx.project.findyourbus.database.SearchEntry;


public class AddSearchViewModel extends ViewModel {

    private LiveData<SearchEntry> search;

    // Note: The constructor should receive the database and the taskId


    public AddSearchViewModel(AppDatabase db, int id) {
        search = db.searchDao().loadSearchById(id);
    }

    public LiveData<SearchEntry> getSearch() {
        return search;
    }
}
