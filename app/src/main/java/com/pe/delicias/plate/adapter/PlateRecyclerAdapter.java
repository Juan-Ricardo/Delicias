package com.pe.delicias.plate.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.Transition;
import com.pe.delicias.R;
import com.pe.delicias.category.model.Category;
import com.pe.delicias.order.OrderSingleton;
import com.pe.delicias.order.model.Order;
import com.pe.delicias.plate.model.Plate;
import com.pe.delicias.utilities.PreferencesSingleton;
import com.pe.delicias.utilities.Utilities;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class PlateRecyclerAdapter extends RecyclerView.Adapter<PlateViewHolder> implements Filterable {

    private List<Plate> plates;
    private List<Plate> filters;
    private int resource;
    private Activity activity;

    public PlateRecyclerAdapter(List<Plate> plates, int resource, Activity activity) {
        this.plates = plates;
        this.filters = plates;
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

        holder.addOrderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity.getBaseContext(), "" + plate.getName(),
                        Toast.LENGTH_SHORT).show();

                Order order=new Order();
                order.setId(UUID.randomUUID().toString());
                order.setDateRegister(new Date().toString());

                String id = PreferencesSingleton.getInstance(activity.getBaseContext()).read(Utilities.ID_CUSTOMER,"default");
                String token = PreferencesSingleton.getInstance(activity.getBaseContext()).read(Utilities.TOKEN_CUSTOMER, "default");
                String names = PreferencesSingleton.getInstance(activity.getBaseContext()).read(Utilities.NAMES_CUSTOMER,"default");

                order.setIdClient(id);
                order.setNameFull(names);

                Plate currentPlate=new Plate();
                currentPlate.setName(plate.getName());
                currentPlate.setDescription(plate.getDescription());

                List<Plate> currentPlates=new LinkedList<>();
                currentPlates.add(currentPlate);

                order.setPlates(currentPlates);
                order.setTotal(75.77);

                OrderSingleton.getInstance(activity.getBaseContext()).addOrder(order);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.plates.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    plates = filters;
                } else {
                    List<Plate> filteredList = new LinkedList<>();
                    for (Plate plate : plates) {
                        if (plate.getName().toLowerCase().contains(charString.toLowerCase()) ||
                                plate.getDescription().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(plate);
                        }
                    }
                    plates = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = plates;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                plates = (List<Plate>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
