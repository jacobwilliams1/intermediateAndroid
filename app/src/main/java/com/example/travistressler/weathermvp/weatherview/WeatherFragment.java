package com.example.travistressler.weathermvp.weatherview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.travistressler.weathermvp.R;
import com.example.travistressler.weathermvp.apicalls.darksky.Weather;
import com.example.travistressler.weathermvp.mainview.MainActivity;
import com.example.travistressler.weathermvp.mainview.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WeatherFragment extends Fragment implements WeatherView {


    private WeatherPresenter presenter;
    @BindView(R.id.layout_background)
    protected RelativeLayout layout;
    @BindView(R.id.area)
    protected TextView area;
    @BindView(R.id.weather_icon)
    protected ImageView weatherIcon;
    @BindView(R.id.summary)
    protected TextView summary;
    @BindView(R.id.temp_hi)
    protected TextView tempHi;
    @BindView(R.id.temp_lo)
    protected TextView tempLo;
    @BindView(R.id.precipitation_chance)
    protected TextView precipitationChance;
    @BindView(R.id.temperature_current)
    protected TextView currentTemperature;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public static WeatherFragment newInstance() {

        Bundle args = new Bundle();

        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter = new WeatherPresenter();
        presenter.attachView(this);
        getWeather();
        getPlace();
    }

    public void getWeather() {
        Weather weather = getArguments().getParcelable(MainPresenter.WEATHER);
        presenter.weatherRetrieved(weather);
    }

    public void getPlace() {
        String place = getArguments().getString(MainPresenter.PLACE);
        presenter.placeRetrieved(place);
    }

    @Override
    public void loadLocation(String place) {
        area.setText(place);
    }

    @Override
    public void showCurrentTemperature(String currentTemp) {
        currentTemperature.setText(currentTemp);
    }

    @Override
    public void showSummary(String summary) {
        this.summary.setText(summary);
    }

    @Override
    public void showTempHi(String tempHi) {
        this.tempHi.setText(tempHi);
    }

    @Override
    public void showTempLo(String tempLo) {
        this.tempLo.setText(tempLo);
    }

    @Override
    public void showPrecipitationChance(String precipChance) {
        this.precipitationChance.setText(precipChance);
    }

    @Override
    public void showCloudyIcon() {
        weatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_cloud));
    }

    @Override
    public void showRainyIcon() {
        weatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_rain));
    }

    @Override
    public void showSnowIcon() {
        weatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_snow));
    }

    @Override
    public void showStormIcon() {
        weatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_storm));
    }

    @Override
    public void showSunnyIcon() {
        weatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_sun));
    }

    @Override
    public void showDefaultIcon() {
        weatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_default));
    }

    @Override
    public void setRainyBackground() {
        layout.setBackgroundColor(getResources().getColor(R.color.rainColor));
    }

    @Override
    public void setSnowyBackground() {
        layout.setBackgroundColor(getResources().getColor(R.color.snowColor));
    }

    @Override
    public void setStormyBackground() {
        layout.setBackgroundColor(getResources().getColor(R.color.stormColor));
    }

    @Override
    public void setSunnyBackground() {
        layout.setBackgroundColor(getResources().getColor(R.color.sunColor));
    }

    @Override
    public void setDefaultBackground() {
        layout.setBackgroundColor(getResources().getColor(R.color.defaultColor));
    }

    @Override
    public void setCloudyBackground() {
        layout.setBackgroundColor(getResources().getColor(R.color.cloudyColor));
    }
}
