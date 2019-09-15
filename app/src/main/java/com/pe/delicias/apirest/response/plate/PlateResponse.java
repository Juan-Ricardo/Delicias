package com.pe.delicias.apirest.response.plate;

import java.util.List;

public class PlateResponse {
    private String message;
    private List<PlateDataResponse> data;

    public PlateResponse() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PlateDataResponse> getData() {
        return data;
    }

    public void setData(List<PlateDataResponse> data) {
        this.data = data;
    }
}
