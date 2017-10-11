package com.example.travistressler.weathermvp.weatherview;


public interface WeatherView {

    void loadLocation(String place);

    void showCurrentTemperature(String s);

    void showSummary(String summary);

    void showTempHi(String s);

    void showTempLo(String s);

    void showPrecipitationChance(String s);

    void showCloudyIcon();

    void showRainyIcon();

    void showSnowIcon();

    void showStormIcon();

    void showSunnyIcon();

    void showDefaultIcon();

    void setRainyBackground();

    void setSnowyBackground();

    void setStormyBackground();

    void setSunnyBackground();

    void setDefaultBackground();

    void setCloudyBackground();
}
