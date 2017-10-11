package com.example.travistressler.weathermvp.mainview;

import android.os.Bundle;


public interface MainView {
    void toastError(String s);

    void setArgumentsOnFragment(Bundle bundle);

    void transitionToWeatherFragment();

    void removeWeatherFragment();

    void closeApp();
//
//    double getLat();
//
//    double getLong();
}
