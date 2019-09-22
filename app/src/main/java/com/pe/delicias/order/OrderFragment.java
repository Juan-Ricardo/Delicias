package com.pe.delicias.order;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.pe.delicias.R;
import com.pe.delicias.order.adapter.OrderAdapterRecycler;
import com.pe.delicias.order.model.Order;
import com.pe.delicias.order.model.OrderModel;
import com.pe.delicias.socket.SocketManager;
import com.pe.delicias.socket.SocketUtils;
import com.pe.delicias.utilities.PreferencesSingleton;
import com.pe.delicias.utilities.Utilities;

import org.json.JSONObject;

import java.util.List;

import io.socket.client.Ack;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {


    private Toolbar toolbar;
    private TextView priceTotalTextView;
    private RecyclerView orderRecyclerView;
    private OrderAdapterRecycler adapter;
    private MaterialButton confirmMaterialButton;

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

    @Override
    public void onStart() {
        super.onStart();
    }

    private void finds(View view) {
        setupToolbar(view, "Órdenes", "", false);
        orderRecyclerView = view.findViewById(R.id.order_recycler_view);
        priceTotalTextView = view.findViewById(R.id.price_total_text_view);
        confirmMaterialButton = view.findViewById(R.id.confirm_material_button);
        confirmMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "Confirmar Pedido", Toast.LENGTH_LONG).show();
                List<Order> orders = OrderSingleton.getInstance(getContext()).getOrders();

                String idClient = PreferencesSingleton.getInstance(getContext())
                        .read(Utilities.ID_CUSTOMER, "default");

                JSONObject jsonObject = SocketUtils.getJsonObject(idClient, orders);
                Log.v("confirmarpedido: ", "" + jsonObject);
                //Log.v("confirmarpedido: ", "antes: " + Thread.currentThread().getName());
                SocketManager.getInstance(getActivity()).emit(SocketUtils.EMIT_ORDER, jsonObject, new Ack() {
                    @Override
                    public void call(Object... args) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Object arg = args[0];
                                Gson gson = new Gson();
                                //Log.v("confirmarpedido: ", "despues: " + Thread.currentThread().getName());
                                OrderModel orderModel = gson.fromJson(arg.toString(), OrderModel.class);
                                if (orderModel.isSuccess()) {
                                    Toast.makeText(getContext(), "Exitosamente!", Toast.LENGTH_LONG).show();
                                    OrderSingleton.getInstance(getContext()).removeAllOrders();
                                    setOrderRecyclerView();
                                    priceTotalTextView.setText("Precio Total S/. 0.0 ");
                                } else {
                                    Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });

            }
        });
    }

    private void setupToolbar(View view, String title, String subTitle, boolean arrow) {
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(subTitle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(arrow);
    }

    @Override
    public void onResume() {
        super.onResume();
        setupSocket();
        setOrderRecyclerView();
        setPriceTotal();
    }

    private void setupSocket() {
        SocketManager.getInstance(getActivity()).connect();
    }

    private void setOrderRecyclerView() {
        List<Order> orders = OrderSingleton.getInstance(getContext()).getOrders();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        orderRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new OrderAdapterRecycler(orders, R.layout.order_card_view, getActivity());
        orderRecyclerView.setAdapter(adapter);
        orderRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    }

    private void setPriceTotal() {
        priceTotalTextView.setText("Precio Total S/. " + OrderSingleton.getInstance(getContext()).getPriceTotal());
    }
}
