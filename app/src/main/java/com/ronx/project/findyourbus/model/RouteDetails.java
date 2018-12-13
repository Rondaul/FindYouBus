package com.ronx.project.findyourbus.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RouteDetails implements Parcelable {

    @SerializedName("Hop")
    @Expose
    private String hop;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("To")
    @Expose
    private String to;
    @SerializedName("From")
    @Expose
    private String from;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("bus_nos")
    @Expose
    private String busNo;

    public RouteDetails() {
    }

    public RouteDetails(String hop, String distance, String to, String from, String duration, String busNo) {
        this.hop = hop;
        this.distance = distance;
        this.to = to;
        this.from = from;
        this.duration = duration;
        this.busNo = busNo;
    }

    protected RouteDetails(Parcel in) {
        hop = in.readString();
        distance = in.readString();
        to = in.readString();
        from = in.readString();
        duration = in.readString();
        busNo = in.readString();
    }

    public static final Creator<RouteDetails> CREATOR = new Creator<RouteDetails>() {
        @Override
        public RouteDetails createFromParcel(Parcel in) {
            return new RouteDetails(in);
        }

        @Override
        public RouteDetails[] newArray(int size) {
            return new RouteDetails[size];
        }
    };

    public String getHop() {
        return hop;
    }

    public void setHop(String hop) {
        this.hop = hop;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hop);
        dest.writeString(distance);
        dest.writeString(to);
        dest.writeString(from);
        dest.writeString(duration);
        dest.writeString(busNo);
    }
}
