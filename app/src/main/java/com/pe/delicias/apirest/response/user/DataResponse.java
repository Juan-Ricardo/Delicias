package com.pe.delicias.apirest.response.user;

public class DataResponse {
    private String token;
    private String nombres;

    public DataResponse() {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
}
