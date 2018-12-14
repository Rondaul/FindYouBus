package com.ronx.project.findyourbus.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViewsService;

import com.ronx.project.findyourbus.model.Route;
import com.ronx.project.findyourbus.utils.Prefs;


public class BusWidgetService extends RemoteViewsService {

    public static final String TAG = BusWidgetService.class.getSimpleName();

    public static void updateWidget(Context context, Route route) {

        Prefs.saveRouteDetails(context, route);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, BusWidgetProvider.class));
        BusWidgetProvider.updateAppWidgets(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        return new ListRemoteViewsFactory(getApplicationContext());
    }

}