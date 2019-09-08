package com.pe.delicias.category.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pe.delicias.category.model.Category;


import java.util.List;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private List<Category> categories;
    private int resource;
    private Activity activity;

    public CategoryRecyclerAdapter(List<Category> categories, int resource, Activity activity) {
        this.categories = categories;
        this.resource = resource;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = this.categories.get(position);
        Glide.with(this.activity)
                .load(category.getImage())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.menuImageView);
        holder.titleTextView.setText(category.getTitle());
        //holder.descriptionTextView.setText(category.getDescription());
    }

    @Override
    public int getItemCount() {
        return this.categories.size();
    }
}
