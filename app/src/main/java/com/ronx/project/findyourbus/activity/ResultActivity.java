package com.ronx.project.findyourbus.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.ronx.project.findyourbus.R;
import com.ronx.project.findyourbus.model.RouteDetails;
import com.ronx.project.findyourbus.rest_api.RetrofitClient;
import com.ronx.project.findyourbus.rest_api.RetrofitInterface;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = ResultActivity.class.getSimpleName();
    public static final String TO = "to";
    public static final String FROM = "from";
    public static final String TYPE = "type";

    @BindView(R.id.tv_from)
    TextView mFromTextView;
    @BindView(R.id.tv_to)
    TextView mToTextView;

    private RetrofitInterface mRetrofitInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);

        String from = getIntent().getExtras().getString(FROM);
        String to = getIntent().getExtras().getString(TO);
        String type = getIntent().getExtras().getString(TYPE);

        mFromTextView.setText(from);
        mToTextView.setText(to);

        mRetrofitInterface = RetrofitClient.getRetrofitClient()
                .create(RetrofitInterface.class);

        Call<HashMap<String,List<RouteDetails>>> call = mRetrofitInterface.getRouteDetails(from, to, type);
        call.enqueue(new Callback<HashMap<String, List<RouteDetails>>>() {
            @Override
            public void onResponse(Call<HashMap<String, List<RouteDetails>>> call, Response<HashMap<String, List<RouteDetails>>> response) {
                Iterator it = response.body().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    List<RouteDetails> list = (List<RouteDetails>) pair.getValue();
                    Log.d(TAG, "onResponse: size" + list.size());
                    Log.d(TAG, "onResponse: "+  pair.getKey() + " = " + pair.getValue());
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, List<RouteDetails>>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }
}
