package com.ronx.project.findyourbus.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ronx.project.findyourbus.R;
import com.ronx.project.findyourbus.database.SearchEntry;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    // Constant for date format
    private static final String DATE_FORMAT = "dd/MM/yyy";

    private OnCancelClickListener mOnCancelClickListener;
    private OnItemClickListener mOnItemClickListener;
    private List<SearchEntry> mSearchEntries;
    private Context mContext;

    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());


    public interface OnCancelClickListener {
        void onCancel(SearchEntry searchEntry);
    }

    public interface OnItemClickListener {
        void onItemClick(SearchEntry searchEntry);
    }


    public SearchAdapter(Context context, OnCancelClickListener onCancelClickListener, OnItemClickListener onItemClickListener) {
        mContext = context;
        mOnCancelClickListener = onCancelClickListener;
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.recent_search_item, viewGroup, false);

        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
            holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return (mSearchEntries == null) ? 0 : mSearchEntries.size();
    }

    public List<SearchEntry> getSearchEntries() {
        return mSearchEntries;
    }

    public void setTasks(List<SearchEntry> searchEntries) {
        mSearchEntries = searchEntries;
        Collections.reverse(mSearchEntries);
        notifyDataSetChanged();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_from)
        TextView mFromTextView;
        @BindView(R.id.tv_to)
        TextView mToTextView;
        @BindView(R.id.iv_cancel)
        ImageView deleteImageView;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(int position) {
            final SearchEntry searchEntry = mSearchEntries.get(position);
            String from = searchEntry.getFrom();
            String to = searchEntry.getTo();

            mFromTextView.setText(from);
            mToTextView.setText(to);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(searchEntry);
                }
            });

            deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnCancelClickListener.onCancel(searchEntry);
                }
            });
        }
    }
}
