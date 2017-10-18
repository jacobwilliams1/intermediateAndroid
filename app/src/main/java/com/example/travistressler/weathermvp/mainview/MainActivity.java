package com.example.travistressler.weathermvp.mainview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travistressler.weathermvp.R;
import com.example.travistressler.weathermvp.weatherview.WeatherFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity implements MainView, LocationListener {

    private MainPresenter presenter;
    private WeatherFragment weatherFragment;
    private static final int REQUEST_LOCATION = 1;
    private LocationManager locationManager;
    private String mprovider;

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

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        mprovider = locationManager.getBestProvider(criteria, false);

        if (mprovider != null && !mprovider.equals("")) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationManager.getLastKnownLocation(mprovider);
            locationManager.requestLocationUpdates(mprovider, 15000, 1, this);

            if (location != null) {
                presenter.getLocation(location.getLongitude(), location.getLatitude());

            }
        }

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


    @Override
    public void onLocationChanged(Location location) {
        TextView longitude = findViewById(R.id.textView);
        TextView latitude = findViewById(R.id.textView1);
        longitude.setText("Current Longitude:" + location.getLongitude());
        latitude.setText("Current Latitude:" + location.getLatitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
    @Override
    public void onBackPressed() {
        presenter.backPressed(weatherFragment.isVisible());
    }

}

