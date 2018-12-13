package com.ronx.project.findyourbus.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ronx.project.findyourbus.R;
import com.ronx.project.findyourbus.adapters.RouteFragmentPagerAdapter;
import com.ronx.project.findyourbus.model.RouteDetails;
import com.ronx.project.findyourbus.rest_api.RetrofitClient;
import com.ronx.project.findyourbus.rest_api.RetrofitInterface;

import java.util.HashMap;
import java.util.List;

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

    @BindView(R.id.route_viewpager)
    ViewPager mRouteViewPager;
    @BindView(R.id.btn_next)
    Button mNextButton;
    @BindView(R.id.btn_previous)
    Button mPreviousButton;
    @BindView(R.id.tv_route_count)
    TextView mRouteCountTextView;
    @BindView(R.id.detail_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    private RetrofitInterface mRetrofitInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);

        String from = getIntent().getExtras().getString(FROM);
        String to = getIntent().getExtras().getString(TO);
        String type = getIntent().getExtras().getString(TYPE);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRetrofitInterface = RetrofitClient.getRetrofitClient()
                .create(RetrofitInterface.class);

        mProgressBar.setVisibility(View.VISIBLE);

        Call<HashMap<String,List<RouteDetails>>> call = mRetrofitInterface.getRouteDetails(from, to, type);
        call.enqueue(new Callback<HashMap<String, List<RouteDetails>>>() {
            @Override
            public void onResponse(Call<HashMap<String, List<RouteDetails>>> call, Response<HashMap<String, List<RouteDetails>>> response) {
                Log.d(TAG, "onResponse: " + response.body().size());
                mProgressBar.setVisibility(View.GONE);
                final RouteFragmentPagerAdapter adapter = new RouteFragmentPagerAdapter(getApplicationContext(), response.body(), getSupportFragmentManager());
                mRouteViewPager.setAdapter(adapter);

                mRouteCountTextView.setText(adapter.getPageTitle(mRouteViewPager.getCurrentItem()));

                mNextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mRouteViewPager.setCurrentItem(mRouteViewPager.getCurrentItem() + 1);
                        mRouteCountTextView.setText(adapter.getPageTitle(mRouteViewPager.getCurrentItem()));
                    }
                });

                mPreviousButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mRouteViewPager.setCurrentItem(mRouteViewPager.getCurrentItem() - 1);
                        mRouteCountTextView.setText(adapter.getPageTitle(mRouteViewPager.getCurrentItem()));
                    }
                });
            }

            @Override
            public void onFailure(Call<HashMap<String, List<RouteDetails>>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int selectedId = item.getItemId();

        if(selectedId == android.R.id.home) {
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
