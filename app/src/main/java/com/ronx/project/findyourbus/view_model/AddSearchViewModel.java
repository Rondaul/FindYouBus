package com.ronx.project.findyourbus.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.ronx.project.findyourbus.database.AppDatabase;
import com.ronx.project.findyourbus.database.SearchEntry;


// COMPLETED (5) Make this class extend ViewModel
public class AddSearchViewModel extends ViewModel {

    // COMPLETED (6) Add a task member variable for the TaskEntry object wrapped in a LiveData
    private LiveData<SearchEntry> search;

    // COMPLETED (8) Create a constructor where you call loadSearchById of the taskDao to initialize the tasks variable
    // Note: The constructor should receive the database and the taskId


    public AddSearchViewModel(AppDatabase db, int id) {
        search = db.searchDao().loadSearchById(id);
    }

    // COMPLETED (7) Create a getter for the task variable
    public LiveData<SearchEntry> getSearch() {
        return search;
    }
}
