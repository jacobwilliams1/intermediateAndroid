package com.example.travistressler.weathermvp.apicalls.darksky;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by travistressler on 9/8/17.
 */

public class Weather implements Parcelable {
    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("currently")
    private CurrentProperties currentProperties;

    @SerializedName("daily")
    private DailyProperties dailyProperties;

    protected Weather(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<Weather> CREATOR = new Creator<Weather>() {
        @Override public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        @Override public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public CurrentProperties getCurrentProperties() {
        return currentProperties;
    }

    public DailyProperties getDailyProperties() {
        return dailyProperties;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
    }

    public class CurrentProperties {

        @SerializedName("summary")
        private String summary;

        @SerializedName("icon")
        private String icon;

        @SerializedName("temperature")
        private double temperature;

        public String getSummary() {
            return summary;
        }

        public double getTemperature() {
            return temperature;
        }
    }

    public class DailyProperties {

        @SerializedName("data")
        private List<DailyData> dailyData;

        public List<DailyData> getDailyData() {
            return dailyData;
        }
    }

    public class DailyData {
        @SerializedName("temperatureMin")
        private double temperatureMin;

        @SerializedName("temperatureMax")
        private double temperatureMax;

        @SerializedName("precipProbability")
        private double precipProbability;

        public double getTemperatureMin() {
            return temperatureMin;
        }

        public double getTemperatureMax() {
            return temperatureMax;
        }

        public double getPrecipProbability() {
            return precipProbability;
        }
    }
}
