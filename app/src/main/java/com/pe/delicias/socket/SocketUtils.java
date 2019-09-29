package com.pe.delicias.socket;

import com.pe.delicias.order.model.Order;
import com.pe.delicias.plate.model.Plate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SocketUtils {
    public static final String EMIT_ORDER = "registrarOrdenDeCompra";

    public static JSONObject getJsonObject(String idClient, Order order) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cliente_id", idClient);
            JSONObject jsonObjectItem;
            JSONArray jsonArray = new JSONArray();
            for (Plate plate : order.getPlates()) {
                jsonObjectItem = new JSONObject();
                jsonObjectItem.put("plato_id", plate.getId());
                jsonObjectItem.put("nombre", plate.getName());
                jsonObjectItem.put("precio", plate.getPrice());
                jsonObjectItem.put("cantidad", plate.getAmount());
                jsonArray.put(jsonObjectItem);
            }
            jsonObject.put("orden", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
