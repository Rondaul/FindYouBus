package com.ronx.project.findyourbus.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.ronx.project.findyourbus.database.AppDatabase;
import com.ronx.project.findyourbus.database.SearchEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<SearchEntry>> seraches;

    public MainViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the seraches from the DataBase");
        seraches = database.searchDao().loadAllSearches();
    }

    public LiveData<List<SearchEntry>> getSearches() {
        return seraches;
    }
}
