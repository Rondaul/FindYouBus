package com.ronx.project.findyourbus.rest_api;

import com.ronx.project.findyourbus.model.RouteDetails;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interface for retrotic to get data through api call.
 */
public interface RetrofitInterface {

    //method to get top_rated movies
    @GET("search/")
    Call<HashMap<String,List<RouteDetails>>> getRouteDetails(@Query("from") String from, @Query("to") String to, @Query("how") String type);
}
