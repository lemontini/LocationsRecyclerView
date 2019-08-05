package com.montini.locationsrecyclerview.model;

import android.net.Uri;

public class Location {
    private String name;
    private String address;
    private int maxCourts;
    Uri logo;

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

}
