package com.montini.locationsrecyclerview.repository;

import android.arch.lifecycle.MutableLiveData;
import android.net.Uri;

import com.montini.locationsrecyclerview.R;
import com.montini.locationsrecyclerview.model.Location;

import java.util.ArrayList;
import java.util.List;

import static com.montini.locationsrecyclerview.MainActivity.getUriForResource;

// Singleton pattern:

public class LocationsRepo {

    private static LocationsRepo instance;
    private ArrayList<Location> dataSet = new ArrayList<>();

    public static LocationsRepo getInstance() {
        if (instance == null){
            instance = new LocationsRepo();
        }
        return instance;
    }

    // Pretend to get data from a webservice or an online resource
    public MutableLiveData<List<Location>> getLocations() {
        setLocations();

        MutableLiveData<List<Location>> data = new MutableLiveData<>();
        data.setValue(dataSet);

        return data;
    }

    private void setLocations() {
        dataSet.add(new Location("SEB arena", "Ąžuolyno g. 7, Vilnius", 4, getUriForResource(R.drawable.logo_seb_arena)));
        dataSet.add(new Location("Delfi Sporto Centras", "Ozo g. 14C, Vilnius", 8, getUriForResource(R.drawable.logo_delfi_sporto_centras)));
        dataSet.add(new Location("Zambia", "Africa", 1, Uri.parse("https://d2lo9qrcc42lm4.cloudfront.net/Images/News/_contentLarge/Main-girls-out-of-school.jpg?mtime=20170426205135")));
        dataSet.add(new Location("a", "b", 1, Uri.parse("file:///storage/emulated/0/Pictures/Instagram/IMG_20190630_210003_297.jpg")));
    }

}
