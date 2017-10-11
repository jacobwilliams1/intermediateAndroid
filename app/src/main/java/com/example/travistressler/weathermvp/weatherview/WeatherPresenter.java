package com.example.travistressler.weathermvp.weatherview;

import android.os.Parcelable;

import com.example.travistressler.weathermvp.R;
import com.example.travistressler.weathermvp.apicalls.darksky.Weather;


public class WeatherPresenter {

    private Weather weather;
    private String place;

    private WeatherView view;

    public void attachView(WeatherView view) {
        this.view = view;
    }

    public void weatherRetrieved(Weather weather) {
        this.weather = weather;
    }

    private void setupView() {
        if(weather != null) {
            view.loadLocation(place);
            view.showCurrentTemperature(String.valueOf((float)Math.ceil(weather.getCurrentProperties().getTemperature())) + (char) 0x00B0);
            view.showSummary(weather.getCurrentProperties().getSummary());
            view.showTempHi(String.valueOf((float)Math.ceil(weather.getDailyProperties().getDailyData().get(0).getTemperatureMax())) + (char) 0x00B0);
            view.showTempLo("/ " + String.valueOf((float)Math.ceil(weather.getDailyProperties().getDailyData().get(0).getTemperatureMin())) + (char) 0x00B0);
            view.showPrecipitationChance(String.valueOf((float)Math.ceil(weather.getDailyProperties().getDailyData().get(0).getPrecipProbability()) * 100) + "%");
            loadIcon();
        }
    }

    private void loadIcon() {
        if(weather.getCurrentProperties().getSummary().contains("Cloudy")) {
            view.showCloudyIcon();
            view.setCloudyBackground();
        } else if (weather.getCurrentProperties().getSummary().contains("Rain")) {
            view.showRainyIcon();
            view.setRainyBackground();
        } else if (weather.getCurrentProperties().getSummary().contains("Snow")) {
            view.showSnowIcon();
            view.setSnowyBackground();
        } else if (weather.getCurrentProperties().getSummary().contains("Storm")) {
            view.showStormIcon();
            view.setStormyBackground();
        } else if (weather.getCurrentProperties().getSummary().contains("Sunny") ||
                weather.getCurrentProperties().getSummary().contains("Clear")) {
            view.showSunnyIcon();
            view.setSunnyBackground();
        } else {
            view.showDefaultIcon();
            view.setDefaultBackground();
        }
    }

    public void placeRetrieved(String place) {
        this.place = place;
        setupView();
    }
}
