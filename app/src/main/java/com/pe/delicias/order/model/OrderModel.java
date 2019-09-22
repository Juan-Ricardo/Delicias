package com.pe.delicias.order.model;

public class OrderModel {
    private boolean success;
    private String message;

    public OrderModel() {

    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
