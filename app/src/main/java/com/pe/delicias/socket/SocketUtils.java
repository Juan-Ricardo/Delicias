package com.pe.delicias.socket;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Ack;

//https://code.tutsplus.com/es/tutorials/android-o-how-to-use-notification-channels--cms-28616
//https://androidmonks.com/android-notifications/
//https://alexzh.com/2018/05/23/notification-for-android/
//https://socket.io/docs/

public class SocketUtils {
    public static final String EMIT_ORDER = "registrarOrdenDeCompra";
    public static JSONObject getJsonObject(String propiedad01, String propiedad02) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("propiedad01", propiedad01);
            jsonObject.put("propiedad02", propiedad02);
            /*JSONObject objItem= null;
            JSONArray jsonArray= new JSONArray();
            for (Item item:items) {
                objItem= new JSONObject();
                objItem.put("propiedad01",item.getDishId());
                objItem.put("propiedad01",item.getName());
                objItem.put("propiedad01",item.getPrice());
                objItem.put("propiedad01",item.getAmount());
                jsonArray.put(objItem);
            }
            obj.put("orden",jsonArray);*/
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }




    /*private void sendOrder(){
        Log.v("CONSOLE", "sendOrder ... socketManager :"+socketManager +
                " isConnected "+socketManager.connected());
        if(socketManager!=null && socketManager.connected()){
            JSONObject jsonObject= Cart.makeOrder();
            socketManager.emit(SocketConstant.EMIT_ORDER, jsonObject, new Ack() {
                @Override
                public void call(Object... args) {
                    Log.v("CONSOLE", "EMIT registrarOrdenDeCompra "+args);
                    Log.v("CONSOLE: ",Thread.currentThread().getName());
                    JSONObject data = (JSONObject) args[0];
                    final SocketResponse socketResponse= new SocketMapper().convert(data);
                    Log.v("CONSOLE", "sendOrder "+socketResponse.toString());
                    if(socketResponse.isSuccess()){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onSuccess();
                            }
                        });
                    }else{
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }
                }
            });
        }
    }*/
}
