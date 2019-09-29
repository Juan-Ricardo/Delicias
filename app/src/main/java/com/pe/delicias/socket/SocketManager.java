package com.pe.delicias.socket;

import android.app.Activity;

import java.net.URISyntaxException;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketManager {

    private static SocketManager SOCKET_MANAGER;
    private Socket socket;
    private Activity activity;

    public SocketManager(Activity context) {
        this.activity = context;
    }

    public static SocketManager getInstance(Activity activity) {
        if (SOCKET_MANAGER == null)
            SOCKET_MANAGER = new SocketManager(activity);
        return SOCKET_MANAGER;
    }

    public void connect() {
        try {
            /*IO.Options options = new IO.Options();
            options.port = 3000;*/
            String url = "https://diplomado-restaurant-backend.herokuapp.com";
            this.socket = IO.socket(url);
            this.socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public boolean connected() {
        return this.socket.connected();
    }

    public Socket disconnect() {
        //this.socket.off(SocketUtils.EMIT_ORDER);
        //this.socket.off(Socket.EVENT_CONNECT);
        return this.socket.disconnect();
    }

    public void on(String event, Emitter.Listener listener) {
        this.socket.on(event, listener);
    }

    public void emit(String event, Object... args) {
        this.socket.emit(event, args);
    }

    public void emit(String event, Object[] args, Ack ack) {
        this.socket.emit(event, args, ack);
    }
}
