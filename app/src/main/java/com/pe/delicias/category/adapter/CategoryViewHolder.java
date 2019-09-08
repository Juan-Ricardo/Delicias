package com.pe.delicias.category.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pe.delicias.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    public ImageView menuImageView;
    public TextView titleTextView;
    public TextView descriptionTextView;


    public CategoryViewHolder(View view) {
        super(view);
        menuImageView = view.findViewById(R.id.menu_image_view);
        titleTextView = view.findViewById(R.id.title_menu_text_view);
        descriptionTextView = view.findViewById(R.id.description_text_view);
    }
}
