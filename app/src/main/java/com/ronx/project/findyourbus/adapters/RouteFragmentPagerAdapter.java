package com.ronx.project.findyourbus.adapters;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ronx.project.findyourbus.R;
import com.ronx.project.findyourbus.fragments.RouteDetailsFragment;
import com.ronx.project.findyourbus.model.RouteDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private List<String> mRoutes = new ArrayList<>();
    private List<List<RouteDetails>> mRoutesListOfList = new ArrayList<>();

    public RouteFragmentPagerAdapter(Context context, HashMap<String, List<RouteDetails>> listHashMap, FragmentManager fm) {
        super(fm);
        mContext = context;
        for (Object o : listHashMap.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            mRoutes.add((String) pair.getKey());
            mRoutesListOfList.add((List<RouteDetails>) pair.getValue());
        }
    }

    @Override
    public Fragment getItem(int i) {
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(RouteDetailsFragment.STEP_KEY, (ArrayList<? extends Parcelable>) mRoutesListOfList.get(i));
        RouteDetailsFragment fragment = new RouteDetailsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return String.format(mContext.getString(R.string.route), position);
    }

    @Override
    public int getCount() {
        return mRoutes.size();
    }
}
