package com.montini.locationsrecyclerview.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.montini.locationsrecyclerview.R;
import com.montini.locationsrecyclerview.databinding.LocationBinding;
import com.montini.locationsrecyclerview.model.Location;
import com.montini.locationsrecyclerview.viewmodel.LocationViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    // constants
    private static final String TAG = "LocationAdapter";

    // vars
    private Context context;
    private List<Location> locations;
    private OnLocationListener onLocationListener;

    // constructor
    public LocationAdapter(Context context, List<Location> locations, OnLocationListener onLocationListener) {
        this.context = context;
        // if (this.locations == null) this.locations = new ArrayList<>();
        this.locations = locations;
        this.onLocationListener = onLocationListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inner_layout, viewGroup, false);
        ViewHolder holder = new ViewHolder(view, onLocationListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: called. Picture: " + locations.get(i).getLogo());

        Picasso.with(context).load(locations.get(i).getLogo()).resize(480, 480).centerInside().into(viewHolder.aLogo);
        viewHolder.aName.setText(locations.get(i).getName());
        viewHolder.aAddress.setText(locations.get(i).getAddress());

        // viewHolder.aItem.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View v) {
        //         Log.d(TAG, "onClick: clicked on: " + locations.get(i));
        //         Toast.makeText(context, locations.get(i).getName(), Toast.LENGTH_SHORT).show();
        //     }
        // });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView aLogo;
        TextView aName, aAddress;
        LinearLayout aItem;
        OnLocationListener onLocationListener;

        public ViewHolder(@NonNull View itemView, OnLocationListener onLocationListener) {
            super(itemView);
            this.aLogo = itemView.findViewById(R.id.vLogo);
            this.aName = itemView.findViewById(R.id.vName);
            this.aAddress = itemView.findViewById(R.id.vAddress);
            this.aItem = itemView.findViewById(R.id.list_line);
            this.onLocationListener = onLocationListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onLocationListener.onLocationClick(getAdapterPosition());
        }
    }

    public interface OnLocationListener {
        void onLocationClick(int position);
    }
    // public LocationAdapter(Context context, List<Location> locations) {
    //     this.context = context;
    //     this.locations = locations;
    // }
    //
    // @NonNull
    // @Override
    // public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    //
    //     View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inner_layout, viewGroup,false);
    //
    //     // RecyclerView.ViewHolder viewHolder = new RecyclerView.ViewHolder() {
    //     // }
    //
    //     return holder;
    // }
    //
    // @Override
    // public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
    //
    // }
    //
    // @Override
    // public void onBindViewHolder(@NonNull LocationView locationView, int position) {
    //
    //     Log.d(TAG, "onBindViewHolder: called.");
    //
    //     // Picasso.with(context).load(locations.get(position).getLogo().toString()).into();
    //
    //     LocationViewModel locationViewModel = locations.get(position);
    //     locationView.bind(locationViewModel);
    // }
    //
    // @Override
    // public int getItemCount() {
    //     return locations.size();
    // }
    //
    // class LocationView extends RecyclerView.ViewHolder {
    //
    //     private LocationBinding locationBinding;
    //
    //     public LocationView(LocationBinding locationBinding) {
    //         super(locationBinding.getRoot());
    //         this.locationBinding = locationBinding;
    //     }
    //
    //     public void bind(LocationViewModel locationViewModel) {
    //         this.locationBinding.setLocationModel(locationViewModel);
    //         locationBinding.executePendingBindings();
    //     }
    //
    //     public LocationBinding getLocationBinding() {
    //         return locationBinding;
    //     }
    // }
}