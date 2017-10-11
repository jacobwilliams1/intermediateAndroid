package com.example.travistressler.weathermvp.apicalls.darksky;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by travistressler on 9/8/17.
 */

public interface DarkSkyApi {
    @GET("{api_key}/{latitude},{longitude}")
    Call<Weather> getWeather(@Path("api_key") String apiKey, @Path("latitude") double latitude, @Path("longitude")
            double longitude);
}
