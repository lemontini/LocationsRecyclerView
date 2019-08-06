package com.montini.locationsrecyclerview.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable {
    private String name;
    private String address;
    private int maxCourts;
    private Uri logo;

    public Location() {}

    public Location(String name, String address, int maxCourts, Uri logo) {
        this.name = name;
        this.address = address;
        this.maxCourts = maxCourts;
        this.logo = logo;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public int getMaxCourts() { return maxCourts; }
    public void setMaxCourts(int maxCourts) { this.maxCourts = maxCourts; }

    public Uri getLogo() { return logo; }
    public void setLogo(Uri logo) { this.logo = logo; }

    // Implementation of Parcelable interface

    protected Location(Parcel in) {
        name = in.readString();
        address = in.readString();
        maxCourts = in.readInt();
        logo = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeInt(maxCourts);
        parcel.writeParcelable(logo, i);
    }

}
