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
    // public class LocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "LocationAdapter";

    private Context context;
    private List<Location> locations = new ArrayList<>();

    public LocationAdapter(Context context, List<Location> locations) {
        this.context = context;
        // if (this.locations == null) this.locations = new ArrayList<>();
        this.locations = locations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inner_layout, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: called.");

        Picasso.with(context).load(locations.get(i).getLogo()).into(viewHolder.aLogo);
        viewHolder.aName.setText(locations.get(i).getName());
        viewHolder.aAddress.setText(locations.get(i).getAddress());

        viewHolder.aItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + locations.get(i));
                Toast.makeText(context, locations.get(i).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView aLogo;
        TextView aName, aAddress;
        LinearLayout aItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.aLogo = itemView.findViewById(R.id.vLogo);
            this.aName = itemView.findViewById(R.id.vName);
            this.aAddress = itemView.findViewById(R.id.vAddress);
            this.aItem = itemView.findViewById(R.id.list_line);
        }
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
