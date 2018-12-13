package com.ronx.project.findyourbus.view_model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.ronx.project.findyourbus.database.AppDatabase;


// COMPLETED (1) Make this class extend ViewModel ViewModelProvider.NewInstanceFactory
public class AddSearchViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    // COMPLETED (2) Add two member variables. One for the database and one for the taskId
    private final AppDatabase mDb;
    private final int mSearchId;

    // COMPLETED (3) Initialize the member variables in the constructor with the parameters received
    public AddSearchViewModelFactory(AppDatabase db, int id) {
        mDb = db;
        mSearchId = id;
    }


    // COMPLETED (4) Uncomment the following method
    // Note: This can be reused with minor modifications
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddSearchViewModel(mDb, mSearchId);
    }
}
