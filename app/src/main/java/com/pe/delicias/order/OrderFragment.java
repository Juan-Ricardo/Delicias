package com.pe.delicias.order;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pe.delicias.R;
import com.pe.delicias.order.model.Order;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {


    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        finds(view);
        return view;
    }


    private void finds(View view) {
        List<Order> orders = OrderSingleton.getInstance(getContext()).getOrders();
        for (Order order : orders) {
            Log.v("order: ", "" + order.getId());
            Log.v("order: ", "" + order.getNameFull());
            Log.v("order: ", "" + order.getPlates().get(0).getName());
        }
    }

}
