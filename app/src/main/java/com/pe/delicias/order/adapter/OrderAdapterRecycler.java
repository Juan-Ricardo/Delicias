package com.pe.delicias.order.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pe.delicias.order.model.Order;

import java.util.List;

public class OrderAdapterRecycler extends RecyclerView.Adapter<OrderViewHolder> {

    private List<Order> orders;
    private Activity activity;
    private int resource;

    public OrderAdapterRecycler(List<Order> orders, int resource, Activity activity) {
        this.orders=orders;
        this.activity=activity;
        this.resource=resource;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = this.orders.get(position);

        holder.idClient.setText(order.getIdClient());
        holder.nameClient.setText(order.getNameFull());
        holder.namePlate.setText(order.getPlate().getName());
        holder.pricePlate.setText("S/. "+order.getPlate().getPrice());
    }

    @Override
    public int getItemCount() {
        return this.orders.size();
    }
}
