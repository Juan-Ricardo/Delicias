package com.pe.delicias.order.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pe.delicias.order.model.Order;
import com.pe.delicias.plate.model.Plate;

import java.util.List;

public class OrderAdapterRecycler extends RecyclerView.Adapter<OrderViewHolder> {

    private List<Plate> plates;
    private String nameClient;
    private Activity activity;
    private int resource;

    public OrderAdapterRecycler(List<Plate> plates, String nameClient, int resource, Activity activity) {
        this.plates = plates;
        this.nameClient = nameClient;
        this.activity = activity;
        this.resource = resource;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Plate plate = this.plates.get(position);
        //holder.idClient.setText(order.getIdClient());
        holder.nameClient.setText(nameClient);
        holder.namePlate.setText("(" + plate.getAmount() + ") " + plate.getName());
        holder.pricePlate.setText("S/. " + plate.getPrice()+" (p.u S/. "+(plate.getPrice()/plate.getAmount())+")");
    }

    @Override
    public int getItemCount() {
        return this.plates.size();
    }
}
