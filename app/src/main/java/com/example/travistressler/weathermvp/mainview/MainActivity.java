package com.example.travistressler.weathermvp.mainview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.travistressler.weathermvp.R;
import com.example.travistressler.weathermvp.weatherview.WeatherFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity implements MainView {

    private MainPresenter presenter;
    private WeatherFragment weatherFragment;
    private static final int REQUEST_LOCATION = 1;

    @BindView(R.id.zipcode)
    EditText zipCode;

    @OnClick(R.id.button_search)
    public void searchClicked(View view) {
        presenter.searchClicked(zipCode.getText().toString());
        zipCode.setText("");
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        ButterKnife.bind(this);
        weatherFragment = WeatherFragment.newInstance();
        presenter = new MainPresenter();
        presenter.attachView(this);

    }


    @Override
    public void toastError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setArgumentsOnFragment(Bundle bundle) {
        weatherFragment.setArguments(bundle);
    }

    @Override
    public void transitionToWeatherFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, weatherFragment).commit();
    }

    @Override
    public void removeWeatherFragment() {
        getSupportFragmentManager().beginTransaction().remove(weatherFragment).commit();
    }

    @Override
    public void closeApp() {
        super.onBackPressed();
    }

//    @Override
//    public double getLat() {
////        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
////
////        @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
////        double latitude = location.getLatitude();
////        return latitude;
//    }

//    @Override
//    public double getLong() {
////        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
////        @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
////        double longitude = location.getLongitude();
////        return longitude;
//    }


    @Override
    public void onBackPressed() {
        presenter.backPressed(weatherFragment.isVisible());
    }

}
