package com.pe.delicias.socket;

import com.pe.delicias.order.model.Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SocketUtils {
    public static final String EMIT_ORDER = "registrarOrdenDeCompra";

    public static JSONObject getJsonObject(String idClient, List<Order> orders) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cliente_id", idClient);
            JSONObject jsonObjectItem;
            JSONArray jsonArray = new JSONArray();
            for (Order order : orders) {
                jsonObjectItem = new JSONObject();
                jsonObjectItem.put("plato_id", order.getPlate().getId());
                jsonObjectItem.put("nombre", order.getPlate().getName());
                jsonObjectItem.put("precio", order.getPlate().getPrice());
                jsonObjectItem.put("cantidad", order.getPlate().getAmount());
                jsonArray.put(jsonObjectItem);
            }
            jsonObject.put("orden", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
