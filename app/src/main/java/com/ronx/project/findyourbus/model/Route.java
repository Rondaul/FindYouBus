package com.ronx.project.findyourbus.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import static com.ronx.project.findyourbus.widget.BusWidgetService.TAG;

public class Route implements Parcelable {

    private List<RouteDetails> mRouteDetailsList;

    public Route(List<RouteDetails> routeDetailsList) {
        mRouteDetailsList = routeDetailsList;
    }

    protected Route(Parcel in) {
        mRouteDetailsList = in.createTypedArrayList(RouteDetails.CREATOR);
    }

    public static final Creator<Route> CREATOR = new Creator<Route>() {
        @Override
        public Route createFromParcel(Parcel in) {
            return new Route(in);
        }

        @Override
        public Route[] newArray(int size) {
            return new Route[size];
        }
    };

    public List<RouteDetails> getRouteDetailsList() {
        return mRouteDetailsList;
    }

    public void setRouteDetailsList(List<RouteDetails> routeDetailsList) {
        mRouteDetailsList = routeDetailsList;
    }

    public static String objectToJson(Route route) {
        Gson gson = new Gson();
        return gson.toJson(route);
    }

    public static Route jsonToObject(String encoded) {

        Log.d(TAG, "jsonToObject: " + encoded);
        Gson gson = new Gson();

        if (encoded.equals("")) {
            return null;
        } else {
            return gson.fromJson(encoded, Route.class);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mRouteDetailsList);
    }
}
