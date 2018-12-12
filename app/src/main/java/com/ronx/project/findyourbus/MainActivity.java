package com.ronx.project.findyourbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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
                R.array.type, android.R.layout.simple_spinner_item);

        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mTypeSpinner.setAdapter(typeAdapter);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String from = mFromAutoTextView.getText().toString();
                String to = mToAutoTextView.getText().toString();
                String type = mTypeSpinner.getSelectedItem().toString();

                Log.d(TAG, "onClick: from = " + from +
                                        "to = " + to +
                                        "type = " + type);
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
