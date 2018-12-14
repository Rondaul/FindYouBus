package com.ronx.project.findyourbus.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ronx.project.findyourbus.R;
import com.ronx.project.findyourbus.model.Route;

public class Prefs {
    public static final String PREFS_NAME = "prefs";

    public static void saveRouteDetails(Context context, Route route) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        prefs.putString(context.getString(R.string.widget_route_key), Route.objectToJson(route));
        prefs.apply();
    }

    public static Route loadRouteDetails(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String jsonString = prefs.getString(context.getString(R.string.widget_route_key), "");

        return "".equals(jsonString) ? null : Route.jsonToObject(jsonString);
    }
}
