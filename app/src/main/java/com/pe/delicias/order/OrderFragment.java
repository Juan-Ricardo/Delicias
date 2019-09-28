package com.pe.delicias.order;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.pe.delicias.R;
import com.pe.delicias.category.model.OrderState;
import com.pe.delicias.order.adapter.OrderAdapterRecycler;
import com.pe.delicias.order.model.Order;
import com.pe.delicias.order.model.OrderModel;
import com.pe.delicias.socket.SocketManager;
import com.pe.delicias.socket.SocketUtils;
import com.pe.delicias.utilities.PreferencesSingleton;
import com.pe.delicias.utilities.Utilities;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import io.socket.client.Ack;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {

    private LinearLayout messageLinearLayout;
    private NestedScrollView orderNestedScrollView;
    private Toolbar toolbar;
    private TextView priceTotalTextView;
    private RecyclerView orderRecyclerView;
    private OrderAdapterRecycler adapter;
    private MaterialButton confirmMaterialButton;
    private MaterialButton deleteMaterialButton;
    private LottieAnimationView successLottieAnimationView;
    private ImageView deliciasImageView;
    private TextView messageTextView;

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
        setupToolbar(view, "Orden de compra", "", false);
        messageLinearLayout = view.findViewById(R.id.message_linear_layout);
        orderNestedScrollView = view.findViewById(R.id.order_nested_scroll_view);
        orderRecyclerView = view.findViewById(R.id.order_recycler_view);
        priceTotalTextView = view.findViewById(R.id.price_total_text_view);
        confirmMaterialButton = view.findViewById(R.id.confirm_material_button);
        confirmMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "Confirmar Pedido", Toast.LENGTH_LONG).show();
                Order order = OrderSingleton.getInstance(getContext()).getOrder();
                if (order.getPlates().size() != 0) {
                    String idClient = PreferencesSingleton.getInstance(getContext())
                            .read(Utilities.ID_CUSTOMER, "default");

                    JSONObject jsonObject = SocketUtils.getJsonObject(idClient, order);
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
                                    OrderModel orderModel = gson.fromJson(arg.toString(), OrderModel.class);
                                    if (orderModel.isSuccess()) {
                                        showSuccess();
                                    } else {
                                        showError();
                                    }
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Agregar plato a la órden", Toast.LENGTH_LONG).show();
                }
            }
        });
        deleteMaterialButton = view.findViewById(R.id.delete_material_button);
        deleteMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repaint();
                showMessage(OrderState.EMPTY);
            }
        });
        successLottieAnimationView = view.findViewById(R.id.success_lottie_animation_view);
        deliciasImageView = view.findViewById(R.id.delicias_image_view);
        messageTextView = view.findViewById(R.id.message_text_view);
        messageTextView.setTypeface(Utilities.sansLight(getContext()));
    }

    private void showSuccess() {
        showMessage(OrderState.ORDER_SUCCESS);
        messageTextView.setText("¡Confirmación Éxitosa!");
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(() -> {
                    hideSuccess();
                    repaint();
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1500);
        priceTotalTextView.setText("Precio Total S/. 0.0 ");
    }

    private void hideSuccess() {
        messageLinearLayout.setVisibility(View.VISIBLE);
        deliciasImageView.setVisibility(View.VISIBLE);
        messageTextView.setText("¡Por favor agregar platos!");
        successLottieAnimationView.setVisibility(View.GONE);
    }

    private void showError() {

    }

    private void hideError() {

    }

    private void repaint() {
        OrderSingleton.getInstance(getContext()).removeOrder();
        OrderSingleton.getInstance(getContext()).setPriceTotal(0.0);
        setOrderRecyclerView();
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
        Order order = OrderSingleton.getInstance(getContext()).getOrder();
        if (order.getPlates().size() > 0) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            orderRecyclerView.setLayoutManager(linearLayoutManager);
            adapter = new OrderAdapterRecycler(order.getPlates(), order.getNameFull(), R.layout.order_card_view, getActivity());
            orderRecyclerView.setAdapter(adapter);
            orderRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
            adapter.notifyDataSetChanged();
        } else {
            showMessage(OrderState.EMPTY);
        }
    }

    private void showMessage(String state) {
        if (state.equalsIgnoreCase(OrderState.EMPTY)) {
            orderNestedScrollView.setVisibility(View.GONE);
            successLottieAnimationView.setVisibility(View.GONE);
            messageLinearLayout.setVisibility(View.VISIBLE);
            confirmMaterialButton.setVisibility(View.GONE);
            deleteMaterialButton.setVisibility(View.GONE);
        } else if (state.equalsIgnoreCase(OrderState.ORDER_SUCCESS)) {
            orderNestedScrollView.setVisibility(View.GONE);
            messageLinearLayout.setVisibility(View.VISIBLE);
            successLottieAnimationView.setVisibility(View.VISIBLE);
            deliciasImageView.setVisibility(View.GONE);
            messageTextView.setVisibility(View.VISIBLE);
            confirmMaterialButton.setVisibility(View.GONE);
            deleteMaterialButton.setVisibility(View.GONE);
        } else {

        }
    }

    private void setPriceTotal() {
        priceTotalTextView.setText("Precio Total S/. " + OrderSingleton.getInstance(getContext()).getPriceTotal());
    }
}
