package com.pe.delicias.apirest.response;

public class UserResponse {
    private String message;
    private DataResponse data;

    public UserResponse() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataResponse getData() {
        return data;
    }

    public void setData(DataResponse data) {
        this.data = data;
    }
}
