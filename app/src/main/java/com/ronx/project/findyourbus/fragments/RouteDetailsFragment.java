package com.ronx.project.findyourbus.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ronx.project.findyourbus.R;
import com.ronx.project.findyourbus.adapters.SingleRouteAdapter;
import com.ronx.project.findyourbus.model.RouteDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RouteDetailsFragment extends Fragment {

    public static final String STEP_KEY = "step_key";

    @BindView(R.id.rv_single_hop_item)
    RecyclerView mSingleRouteRecyclerView;

    private List<RouteDetails> mRouteDetailsList;

    private Unbinder mUnbinder;

    public RouteDetailsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null && getArguments().containsKey(STEP_KEY)) {
            mRouteDetailsList = getArguments().getParcelableArrayList(STEP_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.single_hop_recyclerview_item, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mSingleRouteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SingleRouteAdapter adapter = new SingleRouteAdapter(getContext(),mRouteDetailsList);
        mSingleRouteRecyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
