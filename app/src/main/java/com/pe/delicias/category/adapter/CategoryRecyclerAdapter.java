package com.pe.delicias.category.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pe.delicias.apirest.ApiClient;
import com.pe.delicias.apirest.ApiService;
import com.pe.delicias.apirest.response.plate.PlateDataResponse;
import com.pe.delicias.apirest.response.plate.PlateResponse;
import com.pe.delicias.category.model.Category;
import com.pe.delicias.home.PlateByCategoryListener;
import com.pe.delicias.utilities.Utilities;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private List<Category> categories;
    private int resource;
    private Activity activity;
    private PlateByCategoryListener plateByCategoryListener;

    public CategoryRecyclerAdapter(List<Category> categories, int resource, Activity activity, PlateByCategoryListener listener) {
        this.categories = categories;
        this.resource = resource;
        this.activity = activity;
        this.plateByCategoryListener = listener;
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
        Glide.with(activity)
                .load(category.getImage())
                .into(holder.backgroundCategoryImageView);
        Glide.with(activity)
                .load(category.getImage())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.menuImageView);
        holder.titleTextView.setText(category.getTitle());
        holder.titleTextView.setTypeface(Utilities.sansBlack(activity.getBaseContext()));
        holder.descriptionTextView.setText(category.getDescription());
        holder.descriptionTextView.setTypeface(Utilities.sansLight(activity.getBaseContext()));

        holder.containerRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPlates(category);
            }
        });
    }

    private void loadPlates(Category category) {
        Call<PlateResponse> plateByCategory = ApiClient.getInstance(activity.getBaseContext())
                .createService(ApiService.class)
                .getPlateByCategory(category.getId());
        plateByCategory.enqueue(new Callback<PlateResponse>() {
            @Override
            public void onResponse(Call<PlateResponse> call, Response<PlateResponse> response) {
                PlateResponse plateResponse = response.body();
                for (PlateDataResponse row : plateResponse.getData()) {
                    /*Log.v("plateByCategory: ", "" + row.get_id());
                    Log.v("plateByCategory: ", "" + row.getNombre());
                    Log.v("plateByCategory: ", "" + row.getDescripcion());*/
                }
                plateByCategoryListener.onSuccess(plateResponse);
            }

            @Override
            public void onFailure(Call<PlateResponse> call, Throwable t) {
                Log.v("plateByCategory: ", "onFailure: " + t.getMessage());
                plateByCategoryListener.onError(t.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.categories.size();
    }
}
