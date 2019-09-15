package com.pe.delicias.plate.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pe.delicias.R;

public class PlateViewHolder extends RecyclerView.ViewHolder {
    public ImageView plateImageView;
    public TextView nameTextView;
    public TextView descriptionTextView;
    public TextView priceTextView;

    public PlateViewHolder(@NonNull View itemView) {
        super(itemView);
        plateImageView = itemView.findViewById(R.id.plate_image_view);
        nameTextView = itemView.findViewById(R.id.name_text_view);
        descriptionTextView = itemView.findViewById(R.id.description_text_view);
        priceTextView = itemView.findViewById(R.id.price_text_view);
    }
}
