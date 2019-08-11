package com.montini.locationsrecyclerview.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.montini.locationsrecyclerview.model.Location;
import com.montini.locationsrecyclerview.repository.LocationsRepo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LocationViewModel extends ViewModel {

    // these are here only because of binding TODO: implement later
    public String name;
    public String address;
    public String logo;
    // ------------------

    private MutableLiveData<List<Location>> mLocations;
    private LocationsRepo mRepo;

    public void init() {
        if (mLocations != null) {
            return;
        }
        mRepo = LocationsRepo.getInstance();
        mLocations = mRepo.getLocations();
    }

    public LiveData<List<Location>> getLocations() {
        return mLocations;
    }

    // public String getLogo() {
    //     return logo;
    // }
    //
    // @BindingAdapter({"imgUrl"})
    // public static void loadImage(ImageView imageView, String logo) {
    //     Picasso.with(imageView.getContext()).load(logo).into(imageView);
    // }

    // public LocationViewModel() {
    // }

    // public LocationViewModel(Location location) {
    //     this.name = location.getName();
    //     this.address = location.getAddress();
    //     this.maxCourts = location.getMaxCourts();
    //     this.logoUrl = location.getLogo().toString();
    // }

    // public void addNewEntry(Location location) {
    //     locationViewModels.add(new LocationViewModel(location));
    // }

}
