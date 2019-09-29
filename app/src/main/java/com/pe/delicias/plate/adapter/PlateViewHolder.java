package com.pe.delicias.plate.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.pe.delicias.R;

public class PlateViewHolder extends RecyclerView.ViewHolder {
    public ImageView plateImageView;
    public TextView nameTextView;
    public TextView descriptionTextView;
    public TextView priceTextView;
    public ImageView addOrderImageView;
    public MaterialButton addOrderMaterialButton;
    public LottieAnimationView successLottieAnimationView;
    public RelativeLayout contentRelativeLayout;

    public PlateViewHolder(@NonNull View itemView) {
        super(itemView);
        plateImageView = itemView.findViewById(R.id.plate_image_view);
        nameTextView = itemView.findViewById(R.id.name_text_view);
        descriptionTextView = itemView.findViewById(R.id.description_text_view);
        priceTextView = itemView.findViewById(R.id.price_text_view);
        addOrderImageView = itemView.findViewById(R.id.add_order_image_view);
        addOrderMaterialButton = itemView.findViewById(R.id.add_order_material_button);
        successLottieAnimationView=itemView.findViewById(R.id.successb_lottie_animation_view);
        contentRelativeLayout = itemView.findViewById(R.id.content_relative_layout);
    }
}
