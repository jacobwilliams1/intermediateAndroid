package com.example.travistressler.weathermvp.mainview;

import android.os.Bundle;
import com.example.travistressler.weathermvp.apicalls.darksky.DarkSkyApi;
import com.example.travistressler.weathermvp.apicalls.darksky.Weather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainPresenter {

    private DarkSkyApi darkSkyApi;
    private String darkSkyBaseUrl;
    private String darkSkyApiKey;
    private Retrofit darkSkyRetrofit;
    private Bundle bundle;
    public static String WEATHER = "weather";
    public static String PLACE = "place";
    private double longitude;
    private double latitude;
    private String location;

    public MainPresenter() {
        bundle = new Bundle();
        darkSkyBaseUrl = "https://api.darksky.net/forecast/";
        darkSkyApiKey = "017f3ac0086e46d4ee9d8ea494cd4670";
    }


    private MainView view;

    public void attachView(MainView view) {
        this.view = view;
        if (view != null) {
            buildApi();
        }
    }

    private void buildApi() {
        darkSkyApi = getDarkSkyRetrofit().create(DarkSkyApi.class);
    }

    public void getWeather() {
        try{
            bundle.putString(PLACE, location);
            darkSkyApi.getWeather(darkSkyApiKey, latitude, longitude).enqueue(new Callback<Weather>() {
                @Override
                public void onResponse(Call<Weather> call, Response<Weather> response) {
                    if (response.isSuccessful()) {
                        bundle.putParcelable(WEATHER, response.body());
                        view.setArgumentsOnFragment(bundle);
                        view.transitionToWeatherFragment();
                    }
                }
                @Override
                public void onFailure(Call<Weather> call, Throwable t) {
                }
                        });
                    } catch (Exception e) {
                        view.toastError("Please enter a valid location!");
                    }
                }

    public Retrofit getDarkSkyRetrofit() {
        if (darkSkyRetrofit == null) {
            darkSkyRetrofit = new Retrofit.Builder().baseUrl(darkSkyBaseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return darkSkyRetrofit;
    }

    public void backPressed(boolean weatherFragmentIsVisible) {
        if (weatherFragmentIsVisible) {
            view.removeWeatherFragment();
        } else {
            view.closeApp();
        }
    }

    public void getLocation(double longitude, double latitude,String location) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.location = location;
    }

}
