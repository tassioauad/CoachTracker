package com.tassioauad.trackercoach.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable {

    private Double latitude;
    private Double longitude;

    public Location() {
    }

    public Location(android.location.Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public android.location.Location getLocation() {
        android.location.Location location = new android.location.Location("pedometro");
        location.setLatitude(getLatitude());
        location.setLongitude(getLongitude());

        return location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
    }

    protected Location(Parcel in) {
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
        this.longitude = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
