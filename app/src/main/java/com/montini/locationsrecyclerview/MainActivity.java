package com.montini.locationsrecyclerview;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.montini.locationsrecyclerview.adapter.LocationAdapter;
import com.montini.locationsrecyclerview.model.Location;
import com.montini.locationsrecyclerview.viewmodel.LocationViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationAdapter.OnLocationListener {

    // constants
    private static final String TAG = "MainActivity";
    public final int LOCATION_ADD = 01;
    public final int LOCATION_EDIT = 02;
    public final int RESULT_UNCHANGED = 03;

    // vars
    // private LocationViewModel locationViewModel;
    // private RecyclerView recyclerView;
    private LocationAdapter locationAdapter;
    private List<Location> locations; // = new ArrayList<>()
    private LocationViewModel locationViewModel;

    Toolbar toolbar;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (locations == null) locations = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // overridePendingTransition(0, 0);
        Log.d(TAG, "onCreate: started.");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);

        locationViewModel.init();

        locationViewModel.getLocations().observe(this, new Observer<List<Location>>() {
            @Override
            public void onChanged(@Nullable List<Location> locations) {
                locationAdapter.notifyDataSetChanged();
            }
        });

        initData();
    }

    private void initData() {
        Log.d(TAG, "initData: preparing data.");

        locations.add(new Location("SEB arena", "Ąžuolyno g. 7, Vilnius", 4, getUriForResource(R.drawable.logo_seb_arena)));
        locations.add(new Location("Delfi Sporto Centras", "Ozo g. 14C, Vilnius", 8, getUriForResource(R.drawable.logo_delfi_sporto_centras)));
        locations.add(new Location("Zambia", "Africa", 1, Uri.parse("https://d2lo9qrcc42lm4.cloudfront.net/Images/News/_contentLarge/Main-girls-out-of-school.jpg?mtime=20170426205135")));
        locations.add(new Location("a", "b", 1, Uri.parse("file:///storage/emulated/0/Pictures/Instagram/IMG_20190630_210003_297.jpg")));

        // /storage/emulated/0/Pictures/Instagram/IMG_20190630_210003_297.jpg

        initLocationsView();

        btnAdd = findViewById(R.id.buttonAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddLocationActivity.class);
                startActivityForResult(intent, LOCATION_ADD);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Location location = data.getParcelableExtra("location");
            int position = data.getIntExtra("position", -1);

            switch (requestCode) {
                case LOCATION_EDIT:
                    locations.set(position, location);
                    break;
                case LOCATION_ADD: {
                    locations.add(location);
                    break;
                }
            }
            Log.d(TAG, "onActivityResult: path of the image is: " + location.getLogo());
            locationAdapter.notifyDataSetChanged();
        }
    }

    private void initLocationsView() {
        Log.d(TAG, "initLocationsView: init LocationsView.");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        locationAdapter = new LocationAdapter(this, locations, this);
        recyclerView.setAdapter(locationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onLocationClick(int position) {
        Log.d(TAG, "onLocationClick: clicked: " + position);
        // locations.get(position);
        Intent intent = new Intent(this, AddLocationActivity.class);
        intent.putExtra("location", (Parcelable) locations.get(position));
        intent.putExtra("position", position);
        Log.d(TAG, "onLocationClick: before parcelable: " + locations.get(position).getLogo());
        startActivityForResult(intent, LOCATION_EDIT);
    }

    // @Override
    // protected void onCreate(Bundle savedInstanceState) {
    //     super.onCreate(savedInstanceState);
    //     setContentView(R.layout.activity_main);
    //     // overridePendingTransition(0, 0);
    //     Log.d(TAG, "onCreate: started.");
    //
    //     toolbar = findViewById(R.id.toolbar);
    //     setSupportActionBar(toolbar);
    //
    //     recyclerView = findViewById(R.id.recycler_view);
    //     locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
    //
    //     locationViewModel.init();
    //
    //     locationViewModel.getLocationsLiveData().observe(this, new Observer<List<Location>>() {
    //         @Override
    //         public void onChanged(@Nullable List<Location> locationViewModels) {
    //             locationAdapter.notifyDataSetChanged();
    //         }
    //     });
    //
    //     initLocationsView();
    //
    //     btnAdd = findViewById(R.id.buttonAdd);
    //     btnAdd.setOnClickListener(new View.OnClickListener() {
    //         @Override
    //         public void onClick(View v) {
    //             Intent intent = new Intent(v.getContext(), AddLocationActivity.class);
    //             startActivityForResult(intent, LOCATION_ADD);
    //         }
    //     });
    // }
    //
    // @Override
    // public void onActivityResult(int requestCode, int resultCode, Intent data) {
    //     super.onActivityResult(requestCode, resultCode, data);
    //     if (requestCode == LOCATION_ADD && resultCode == RESULT_OK) {
    //         // locationViewModel.addNewEntry(new Location(
    //         //         data.getStringExtra("name"),
    //         //         data.getStringExtra("address"),
    //         //         Integer.valueOf(data.getStringExtra("maxCourts")),
    //         //         Uri.parse(data.getStringExtra("logo"))));
    //     }
    //     locationAdapter.notifyDataSetChanged();
    //     // TODO: Returns new Location as it should, but if yet another Location is added,
    //     // TODO: the photo of the one before disappears
    //     // TODO: Also, after rotating the screen of smartphone to landscape, all Locations added via Intents disappear
    // }
    //
    // private void initLocationsView() {
    //     locationAdapter = new LocationAdapter(this, locationViewModel.getLocationsLiveData().getValue());
    //     recyclerView.setLayoutManager(new LinearLayoutManager(this));
    //     recyclerView.setAdapter(locationAdapter);
    // }
    //
    // private void setData() {
    //     LocationViewModel locationViewModel = new LocationViewModel();
    //     locationViewModel.init();
    // }
    //
    // // @Override
    // // protected void onResume() {
    // //     overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    // //     super.onResume();
    // // }
    //
    public static Uri getUriForResource(int resourceId) {
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resourceId);
    }
}
