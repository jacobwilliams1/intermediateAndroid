package com.example.travistressler.weathermvp.apicalls.google;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by travistressler on 9/8/17.
 */

public interface GoogleGeoApi {
    @GET("json")
    Call<GoogleAddress> getAddress(@Query("address") String address, @Query("api_key") String apiKey);
}
