package com.eceakilli.javaworkmanager;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestInterface {


    @GET("all")
    Call<Map<String, Double>> getWorldData();

}
