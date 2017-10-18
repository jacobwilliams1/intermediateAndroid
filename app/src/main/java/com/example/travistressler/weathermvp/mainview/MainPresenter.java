package com.example.travistressler.weathermvp.mainview;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.example.travistressler.weathermvp.R;
import com.example.travistressler.weathermvp.apicalls.darksky.DarkSkyApi;
import com.example.travistressler.weathermvp.apicalls.darksky.Weather;
import com.example.travistressler.weathermvp.apicalls.google.GoogleAddress;
import com.example.travistressler.weathermvp.apicalls.google.GoogleGeoApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainPresenter {

    private GoogleGeoApi googleGeoApi;
    private DarkSkyApi darkSkyApi;
    private String googleBaseUrl;
    private String darkSkyBaseUrl;
    private String googleApiKey;
    private String darkSkyApiKey;
    private Retrofit googleRetrofit;
    private Retrofit darkSkyRetrofit;
    private Bundle bundle;
    private LocationManager locationManager;
    public static String WEATHER = "weather";
    public static String PLACE = "place";
    private double longitude;
    private double latitude;

    public MainPresenter() {
        bundle = new Bundle();
        googleBaseUrl = "https://maps.googleapis.com/maps/api/geocode/";
        darkSkyBaseUrl = "https://api.darksky.net/forecast/";
        googleApiKey = "AIzaSyDJPBfg8tgmDSRF-Sl6gdT3KDfs4gL_uhg";
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
        googleGeoApi = getGoogleRetrofit().create(GoogleGeoApi.class);
        darkSkyApi = getDarkSkyRetrofit().create(DarkSkyApi.class);
    }

    public void searchClicked(String address) {
        getWeather(address);

    }

    public void getWeather(String address) {
        googleGeoApi.getAddress(address, googleApiKey).enqueue(new Callback<GoogleAddress>() {
            @Override
            public void onResponse(Call<GoogleAddress> call, Response<GoogleAddress> response) {
                if (response.isSuccessful()) {
                    try {
                        bundle.putString(PLACE, response.body().getResults().get(0).getAddressName());
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
            }

            @Override
            public void onFailure(Call<GoogleAddress> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public Retrofit getGoogleRetrofit() {
        if (googleRetrofit == null) {
            googleRetrofit = new Retrofit.Builder().baseUrl(googleBaseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return googleRetrofit;
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

    public void getLocation(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
