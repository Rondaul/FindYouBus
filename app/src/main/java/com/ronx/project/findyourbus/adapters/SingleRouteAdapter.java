package com.ronx.project.findyourbus.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ronx.project.findyourbus.R;
import com.ronx.project.findyourbus.model.RouteDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SingleRouteAdapter extends RecyclerView.Adapter<SingleRouteAdapter.SingleRouteViewHolder> {

    private Context mContext;
    private List<RouteDetails> mRouteDetailsList;

    public SingleRouteAdapter(Context context, List<RouteDetails> routeDetailsList) {
        mContext = context;
        mRouteDetailsList = routeDetailsList;
    }

    @NonNull
    @Override
    public SingleRouteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.route_layout_item, viewGroup, false);

        return new SingleRouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleRouteViewHolder holder, int position) {
                holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mRouteDetailsList.size();
    }

    public class SingleRouteViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.hop_no)
        TextView mHopNoTextView;
        @BindView(R.id.tv_from)
        TextView mFromTextView;
        @BindView(R.id.tv_to)
        TextView mToTextView;
        @BindView(R.id.tv_distance)
        TextView mDistanceTextView;
        @BindView(R.id.tv_duration)
        TextView mDurationTextView;
        @BindView(R.id.tv_type)
        TextView mTypeTextView;
        @BindView(R.id.rv_bus_nos)
        RecyclerView mBusNoRecycleView;
        public SingleRouteViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(int position) {
            RouteDetails routeDetails = mRouteDetailsList.get(position);
            mFromTextView.setText(routeDetails.getFrom());
            mHopNoTextView.setText(String.format("Hop: %s", routeDetails.getHop()));
            mToTextView.setText(routeDetails.getTo());

        }
    }
}