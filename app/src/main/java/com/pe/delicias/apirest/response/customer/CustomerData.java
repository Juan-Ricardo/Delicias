package com.pe.delicias.apirest.response.customer;

import com.pe.delicias.apirest.request.UserModel;

import java.util.Date;

public class CustomerData extends UserModel {
    private boolean active;
    private String fechaRegistro;

    public CustomerData() {
        super();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

}
