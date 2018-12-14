package com.ronx.project.findyourbus.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.ronx.project.findyourbus.R;
import com.ronx.project.findyourbus.model.Route;
import com.ronx.project.findyourbus.utils.Prefs;


public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private Route mRoute;

    private String [] splitArrays;

    public ListRemoteViewsFactory(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mRoute = Prefs.loadRouteDetails(mContext);
        if(mRoute != null) {
            String busNo = mRoute.getRouteDetailsList().get(0).getBusNo();
            splitArrays = busNo.split(", ");
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return splitArrays.length;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.bus_widget_list_item);

        remoteViews.setTextViewText(R.id.tv_single_bus_no, splitArrays[position]);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
