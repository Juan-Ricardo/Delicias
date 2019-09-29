package com.pe.delicias.order;

import android.content.Context;
import android.util.Log;

import com.pe.delicias.order.model.Order;
import com.pe.delicias.plate.model.Plate;
import com.pe.delicias.utilities.PreferencesSingleton;
import com.pe.delicias.utilities.Utilities;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class OrderSingleton {

    private Order order;
    private double priceTotal = 0;
    private static OrderSingleton INSTANCE;
    private Context context;
    private List<Plate> plates;

    public OrderSingleton(Context context) {
        this.context = context;
        this.plates = new LinkedList<>();
    }

    public static OrderSingleton getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new OrderSingleton(context);
        }
        return INSTANCE;
    }

    public void addPlate(Plate plate) {
        validatePlates(plate);
    }

    public List<Plate> getPlates() {
        return this.plates;
    }

    private void validatePlates(Plate plate) {
        boolean flag = false;
        if (this.plates.size() == 0) {
            this.plates.add(plate);
        } else {
            for (int i = 0; i < this.plates.size(); i++) {
                Plate currentPlate = this.plates.get(i);
                if (plate.getId().equalsIgnoreCase(currentPlate.getId())) {
                    int amount = currentPlate.getAmount();
                    double price = currentPlate.getPrice();
                    plate.setAmount(amount + 1);
                    plate.setPrice(price * 2);
                    this.plates.set(i, plate);
                    flag = true;
                }
            }
            if (!flag) {
                plates.add(plate);
                flag = false;
            }
        }
    }

    public double getPriceTotal() {
        this.priceTotal = 0;
        for (Plate plate : this.plates)
            this.priceTotal = this.priceTotal + plate.getPrice();
        return this.priceTotal;
    }

    public void setPriceTotal(double priceTotal) {
        this.priceTotal = priceTotal;
    }

    public Order getOrder() {
        this.order = new Order();
        this.order.setId(UUID.randomUUID().toString());
        this.order.setDateRegister(new Date().toString());

        String id = PreferencesSingleton.getInstance(context).read(Utilities.ID_CUSTOMER, "default");
        //String token = PreferencesSingleton.getInstance(context).read(Utilities.TOKEN_CUSTOMER, "default");
        String names = PreferencesSingleton.getInstance(context).read(Utilities.NAMES_CUSTOMER, "default");

        this.order.setIdClient(id);
        this.order.setNameFull(names);
        this.order.setPlates(getPlates());

        return this.order;
    }

    public void removeOrder() {
        this.order = new Order();
        removeAllPlates();
    }

    private void removeAllPlates() {
        this.plates = new LinkedList<>();
    }
}
