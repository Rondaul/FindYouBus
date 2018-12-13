package com.ronx.project.findyourbus.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.ronx.project.findyourbus.R;
import com.ronx.project.findyourbus.adapters.SearchAdapter;
import com.ronx.project.findyourbus.database.AppDatabase;
import com.ronx.project.findyourbus.database.SearchEntry;
import com.ronx.project.findyourbus.utils.AppExecutors;
import com.ronx.project.findyourbus.view_model.MainViewModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.auto_tv_from)
    AutoCompleteTextView mFromAutoTextView;
    @BindView(R.id.auto_tv_to)
    AutoCompleteTextView mToAutoTextView;
    @BindView(R.id.type_spinner)
    Spinner mTypeSpinner;
    @BindView(R.id.btn_search)
    Button mSearchButton;
    @BindView(R.id.rv_recent_searches)
    RecyclerView mRecentSearchesRecyclerView;

    private SearchAdapter mSearchAdapter;

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecentSearchesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSearchAdapter = new SearchAdapter(this, new SearchAdapter.OnCancelClickListener() {
            @Override
            public void onCancel(final SearchEntry searchEntry) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        mDb.searchDao().deleteSearch(searchEntry);
                        return null;
                    }
                }.execute();
            }
        }, new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SearchEntry searchEntry) {
                String from = searchEntry.getFrom();
                String to = searchEntry.getTo();
                String type = searchEntry.getType();

                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra(ResultActivity.FROM, from);
                intent.putExtra(ResultActivity.TO, to);
                intent.putExtra(ResultActivity.TYPE, type);

                if(intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        mRecentSearchesRecyclerView.setAdapter(mSearchAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        int position = viewHolder.getAdapterPosition();
                        List<SearchEntry> searchEntries = mSearchAdapter.getSearchEntries();
                        mDb.searchDao().deleteSearch(searchEntries.get(position));
                        return null;
                    }
                }.execute();
            }
        }).attachToRecyclerView(mRecentSearchesRecyclerView);

        mDb = AppDatabase.getInstance(getApplicationContext());
        setupViewModel();

        try {
            JSONArray jsonArray = new JSONArray(loadJSONFromAsset());
            List<String> locationLists = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                locationLists.add((String) jsonArray.get(i));
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, locationLists);
            mFromAutoTextView.setThreshold(1);
            mToAutoTextView.setThreshold(1);
            mFromAutoTextView.setAdapter(arrayAdapter);
            mToAutoTextView.setAdapter(arrayAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.type, R.layout.type_spinner_layout);

        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mTypeSpinner.setAdapter(typeAdapter);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String from = mFromAutoTextView.getText().toString();
                final String to = mToAutoTextView.getText().toString();
                String type = mTypeSpinner.getSelectedItem().toString();
                Date date = new Date();

                Log.d(TAG, "onClick: from = " + from +
                        "to = " + to +
                        "type = " + type);

                if(from.equals("") || to.equals("") || type.equals("")) {
                    Toast.makeText(MainActivity.this, getString(R.string.empty_string_message), Toast.LENGTH_SHORT).show();
                } else {
                    final SearchEntry searchEntry = new SearchEntry(from, to, type, date);
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            List<SearchEntry> searchEntries = mSearchAdapter.getSearchEntries();
                            for(SearchEntry entry : searchEntries) {
                                if(entry.getFrom().equals(from) && entry.getTo().equals(to)) {
                                    mDb.searchDao().deleteSearch(entry);
                                }
                            }
                            mDb.searchDao().insertSearch(searchEntry);
                        }
                    });

                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                    intent.putExtra(ResultActivity.FROM, from);
                    intent.putExtra(ResultActivity.TO, to);
                    intent.putExtra(ResultActivity.TYPE, type);

                    if(intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getSearches().observe(this, new Observer<List<SearchEntry>>() {
            @Override
            public void onChanged(@Nullable List<SearchEntry> taskEntries) {
                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                mSearchAdapter.setTasks(taskEntries);
            }
        });
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("bus_list.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
