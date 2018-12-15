package com.ronx.project.findyourbus.view_model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.ronx.project.findyourbus.database.AppDatabase;


public class AddSearchViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final int mSearchId;

    public AddSearchViewModelFactory(AppDatabase db, int id) {
        mDb = db;
        mSearchId = id;
    }


    // Note: This can be reused with minor modifications
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddSearchViewModel(mDb, mSearchId);
    }
}
