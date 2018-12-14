package com.ronx.project.findyourbus.widget;

import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.ronx.project.findyourbus.R;
import com.ronx.project.findyourbus.model.Route;
import com.ronx.project.findyourbus.utils.Prefs;

import static com.ronx.project.findyourbus.widget.BusWidgetService.TAG;


public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private Route mRoute;

    public ListRemoteViewsFactory(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mRoute = Prefs.loadRouteDetails(mContext);
        Log.d(TAG, "bus No provider: " + mRoute.getRouteDetailsList().get(0).getBusNo());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mRoute.getRouteDetailsList().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.bus_widget_list_item);
        remoteViews.setTextViewText(R.id.tv_from, mRoute.getRouteDetailsList().get(position).getFrom());
        remoteViews.setTextViewText(R.id.tv_to, mRoute.getRouteDetailsList().get(position).getTo());
        remoteViews.setTextViewText(R.id.tv_distance, mRoute.getRouteDetailsList().get(position).getDistance());
        remoteViews.setTextViewText(R.id.tv_duration, mRoute.getRouteDetailsList().get(position).getDuration());
        remoteViews.setTextViewText(R.id.tv_bus_no, mRoute.getRouteDetailsList().get(position).getBusNo());

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
