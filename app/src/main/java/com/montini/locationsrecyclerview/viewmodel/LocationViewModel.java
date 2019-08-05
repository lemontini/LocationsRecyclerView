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

    public String name;
    public String address;
    private int maxCourts;
    public String logoUrl;

    private MutableLiveData<List<Location>> locationsLiveData; // = new MutableLiveData<>();
    // private ArrayList<LocationViewModel> locationViewModels = new ArrayList<>();
    private LocationsRepo repo;

    public String getLogoUrl() {
        return logoUrl;
    }

    @BindingAdapter({"imgUrl"})
    public static void loadImage(ImageView imageView, String logoUrl) {
        Picasso.with(imageView.getContext()).load(logoUrl).into(imageView);
    }

    public LocationViewModel() {
    }

    public void init() {
        if (locationsLiveData != null) {
            return;
        }
        repo = LocationsRepo.getInstance();
        locationsLiveData = repo.getLocations();
    }

    // public LocationViewModel(Location location) {
    //     this.name = location.getName();
    //     this.address = location.getAddress();
    //     this.maxCourts = location.getMaxCourts();
    //     this.logoUrl = location.getLogo().toString();
    // }

    public LiveData<List<Location>> getLocationsLiveData() {

        // locationViewModels = new ArrayList<>();

        // Location location = new Location("Dummy", "Address, City", 1, getUriForResource(R.drawable.placeholder_camera));
        //
        // LocationViewModel locationViewModel = new LocationViewModel(location);
        //
        // locationViewModels.add(new LocationViewModel(new Location("SEB arena", "Ąžuolyno g. 7, Vilnius", 4, getUriForResource(R.drawable.logo_seb_arena))));
        // locationViewModels.add(new LocationViewModel(new Location("Delfi Sporto Centras", "Ozo g. 14C, Vilnius", 8, getUriForResource(R.drawable.logo_delfi_sporto_centras))));
        // locationViewModels.add(locationViewModel);
        // locationViewModels.add(new LocationViewModel(new Location("Zambia", "Africa", 1, Uri.parse("https://d2lo9qrcc42lm4.cloudfront.net/Images/News/_contentLarge/Main-girls-out-of-school.jpg?mtime=20170426205135"))));

        // arrayListMutableLiveData.setValue(locationViewModels);

        return locationsLiveData;
    }

    // public void addNewEntry(Location location) {
    //     locationViewModels.add(new LocationViewModel(location));
    // }

}
