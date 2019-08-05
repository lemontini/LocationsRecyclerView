package com.montini.locationsrecyclerview.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.montini.locationsrecyclerview.AddLocationActivity;
import com.montini.locationsrecyclerview.MainActivity;
import com.montini.locationsrecyclerview.R;
import com.montini.locationsrecyclerview.databinding.LocationBinding;
import com.montini.locationsrecyclerview.model.Location;
import com.montini.locationsrecyclerview.presenter.Presenter;
import com.montini.locationsrecyclerview.viewmodel.LocationViewModel;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationView> {

    private Context context;
    private List<Location> locations = new ArrayList<>();
    private LayoutInflater layoutInflater;

    private static final String TAG = "LocationAdapter";

    public LocationAdapter(Context context, List<Location> locations) {
        this.context = context;
        this.locations = locations;
    }

    @NonNull
    @Override
    public LocationView onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }

        final LocationBinding locationBinding = DataBindingUtil.inflate(layoutInflater, R.layout.innerlayout, viewGroup, false);

        locationBinding.setPresenter(new Presenter() {
            @Override
            public void onLocationEvent() {
                Toast.makeText(context, locationBinding.getLocationModel().name, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, AddLocationActivity.class);
                i.putExtra("name", locationBinding.getLocationModel().name);
                // context.startActivity(i);
                ((MainActivity) context).startActivityForResult(i,01);
                // ((MainActivity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        LocationView holder = new LocationView(locationBinding);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LocationView locationView, int position) {

        Log.d(TAG, "onBindViewHolder: called.");

        // Picasso.with(context).load(locations.get(position).getLogo().toString()).into();


        // LocationViewModel locationViewModel = locations.get(position);
        // locationView.bind(locationViewModel);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    class LocationView extends RecyclerView.ViewHolder {

        private LocationBinding locationBinding;

        public LocationView(LocationBinding locationBinding) {
            super(locationBinding.getRoot());
            this.locationBinding = locationBinding;
        }

        public void bind(LocationViewModel locationViewModel) {
            this.locationBinding.setLocationModel(locationViewModel);
            locationBinding.executePendingBindings();
        }

        public LocationBinding getLocationBinding() {
            return locationBinding;
        }
    }
}
