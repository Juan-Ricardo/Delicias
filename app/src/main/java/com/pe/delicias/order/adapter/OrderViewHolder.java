package com.pe.delicias.order.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pe.delicias.R;

public class OrderViewHolder extends RecyclerView.ViewHolder {
    public TextView idClient;
    public TextView nameClient;
    //private TextView idPlato;
    public TextView namePlate;
    public TextView pricePlate;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        idClient=itemView.findViewById(R.id.id_customer_text_view);
        nameClient=itemView.findViewById(R.id.name_customer_text_view);
        //idPlato=itemView.findViewById(R.id.id_customer_text_view);
        namePlate=itemView.findViewById(R.id.plate_text_view);
        pricePlate=itemView.findViewById(R.id.price_text_view);
    }
}
