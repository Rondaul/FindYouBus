package com.ronx.project.findyourbus.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.ronx.project.findyourbus.R;
import com.ronx.project.findyourbus.activity.MainActivity;
import com.ronx.project.findyourbus.model.Route;
import com.ronx.project.findyourbus.utils.Prefs;

/**
 * Implementation of App Widget functionality.
 */
public class BusWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Route route = Prefs.loadRouteDetails(context);

        if(route != null) {
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);

            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bus_widget_provider);

            views.setTextViewText(R.id.tv_from, "From: " + route.getRouteDetailsList().get(0).getFrom());
            views.setTextViewText(R.id.tv_to, "To: " + route.getRouteDetailsList().get(0).getTo());

            // Widgets allow click handlers to only launch pending intents
            views.setOnClickPendingIntent(R.id.recent_search, pendingIntent);
//            views.setOnClickPendingIntent(R.id.lv_search, pendingIntent);

            // Initialize the list view
            Intent intent = new Intent(context, BusWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            // Bind the remote adapter
            views.setRemoteAdapter(R.id.lv_search, intent);

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.lv_search);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

