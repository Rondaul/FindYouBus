package com.ronx.project.findyourbus.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RouteDetails implements Serializable {

    @SerializedName("Hop")
    private String hop;
    @SerializedName("distance")
    private String distance;
    @SerializedName("To")
    private String to;
    @SerializedName("From")
    private String from;
    @SerializedName("duration")
    private String duration;
    @SerializedName("bus_nos")
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
}
