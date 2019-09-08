package com.pe.delicias.apirest.response.customer;

import com.google.gson.annotations.SerializedName;
import com.pe.delicias.apirest.request.UserModel;

import java.util.Date;

public class CustomerResponse {
    private String message;
    private CustomerData data;

    public CustomerResponse() {
        super();
    }

    public CustomerData getData() {
        return data;
    }

    public void setData(CustomerData data) {
        this.data = data;
    }
}
