package com.ronx.project.findyourbus.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.ronx.project.findyourbus.R;
import com.ronx.project.findyourbus.adapters.RouteFragmentPagerAdapter;
import com.ronx.project.findyourbus.model.Route;
import com.ronx.project.findyourbus.model.RouteDetails;
import com.ronx.project.findyourbus.rest_api.RetrofitClient;
import com.ronx.project.findyourbus.rest_api.RetrofitInterface;
import com.ronx.project.findyourbus.utils.SnackbarFactory;
import com.ronx.project.findyourbus.widget.BusWidgetService;

import java.util.HashMap;
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
    private static final String PROGRESSBAR_STATUS = "progressbar_status";

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
    @BindView(android.R.id.content)
    View mParentLayout;
    @BindView(R.id.adView)
    AdView mAdView;

    private Route mRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            // inside your activity (if you did not enable transitions in your theme)
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
// set an enter transition
            getWindow().setEnterTransition(new Explode());
// set an exit transition
            getWindow().setExitTransition(new Explode());

        }

        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);

        MobileAds.initialize(this, getString(R.string.ad_id));
        initAddMob();

        if (getIntent().getExtras() != null) {
            String from = getIntent().getExtras().getString(FROM);
            String to = getIntent().getExtras().getString(TO);
            String type = getIntent().getExtras().getString(TYPE);

            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            RetrofitInterface retrofitInterface = RetrofitClient.getRetrofitClient()
                    .create(RetrofitInterface.class);

            mProgressBar.setVisibility(View.VISIBLE);

            Call<HashMap<String, List<RouteDetails>>> call = retrofitInterface.getRouteDetails(from, to, type);
            call.enqueue(new Callback<HashMap<String, List<RouteDetails>>>() {

                @Override
                public void onResponse(@NonNull Call<HashMap<String, List<RouteDetails>>> call, @NonNull Response<HashMap<String, List<RouteDetails>>> response) {
                    mProgressBar.setVisibility(View.GONE);
                    HashMap<String, List<RouteDetails>> hashMap = response.body();
                    if (hashMap != null && hashMap.entrySet().iterator().hasNext()) {
                        Log.d(TAG, "onResponse: " + hashMap.size());
                        Map.Entry<String, List<RouteDetails>> entry = hashMap.entrySet().iterator().next();
                        mRoute = new Route(entry.getValue());

                        final RouteFragmentPagerAdapter adapter = new RouteFragmentPagerAdapter(getApplicationContext(), hashMap, getSupportFragmentManager());
                        mRouteViewPager.setAdapter(adapter);

                        mRouteCountTextView.setText(adapter.getPageTitle(mRouteViewPager.getCurrentItem()));

                        mRouteViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int i, float v, int i1) {

                            }

                            @Override
                            public void onPageSelected(int i) {
                                mRouteCountTextView.setText(adapter.getPageTitle(i));
                            }

                            @Override
                            public void onPageScrollStateChanged(int i) {

                            }
                        });

                        mNextButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mRouteViewPager.setCurrentItem(mRouteViewPager.getCurrentItem() + 1);

                            }
                        });

                        mPreviousButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mRouteViewPager.setCurrentItem(mRouteViewPager.getCurrentItem() - 1);
                            }
                        });
                    } else {
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(ResultActivity.this, getString(R.string.error_message), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<HashMap<String, List<RouteDetails>>> call, @NonNull Throwable t) {
                    Log.d(TAG, "onFailure: " + t.toString());
                }
            });
        } else {
            SnackbarFactory.makeSnackBar(this, mParentLayout, getString(R.string.no_input), false);
        }
    }

    private void initAddMob() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int selectedId = item.getItemId();

        if (selectedId == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        } else if (selectedId == R.id.action_add_to_widget) {
            String message;
            if (mRoute != null) {
                BusWidgetService.updateWidget(this, mRoute);
                message = getString(R.string.added_to_widget);
            } else {
                message = getString(R.string.error_message);
            }
            SnackbarFactory.makeSnackBar(this, mParentLayout, message, false);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mRoute != null) {
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
