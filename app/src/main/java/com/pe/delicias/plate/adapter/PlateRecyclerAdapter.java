package com.pe.delicias.plate.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.Transition;
import com.pe.delicias.R;
import com.pe.delicias.category.model.Category;
import com.pe.delicias.plate.model.Plate;
import com.pe.delicias.utilities.Utilities;

import java.util.List;

public class PlateRecyclerAdapter extends RecyclerView.Adapter<PlateViewHolder> {

    private List<Plate> plates;
    private int resource;
    private Activity activity;

    public PlateRecyclerAdapter(List<Plate> plates, int resource, Activity activity) {
        this.plates = plates;
        this.resource = resource;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PlateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new PlateViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlateViewHolder holder, int position) {
        Plate plate = this.plates.get(position);

        String url = "https://diplomado-restaurant-backend.herokuapp.com/images/platos/" + plate.getImage();
        Glide.with(activity.getBaseContext())
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.salads)
                .error(R.drawable.salads)
                .into(holder.plateImageView);

        holder.nameTextView.setText(plate.getName());
        holder.nameTextView.setTypeface(Utilities.sansBold(activity.getBaseContext()));

        holder.descriptionTextView.setText(plate.getDescription());
        holder.descriptionTextView.setTypeface(Utilities.sansLight(activity.getBaseContext()));

        holder.priceTextView.setText("S/. " + plate.getPrice());
        holder.priceTextView.setTypeface(Utilities.sansBold(activity.getBaseContext()));
    }

    @Override
    public int getItemCount() {
        return this.plates.size();
    }
}
