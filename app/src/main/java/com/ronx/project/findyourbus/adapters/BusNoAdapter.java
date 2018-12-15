package com.ronx.project.findyourbus.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ronx.project.findyourbus.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BusNoAdapter extends RecyclerView.Adapter<BusNoAdapter.BusNoViewHolder> {

    private Context mContext;
    private List<String> busNoList;

    BusNoAdapter(Context context, List<String> busNoList) {
        mContext = context;
        this.busNoList = busNoList;
    }

    @NonNull
    @Override
    public BusNoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.bus_no_item, viewGroup, false);
        return new BusNoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusNoViewHolder holder, int position) {
            holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return busNoList.size();
    }

    class BusNoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.bus_no)
        TextView mBusNoTextView;

        BusNoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(int position) {
            String busNo = busNoList.get(position);
            mBusNoTextView.setText(busNo);
        }
    }
}
