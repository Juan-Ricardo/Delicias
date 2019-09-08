package com.pe.delicias.apirest.response;

import com.pe.delicias.apirest.request.UserModel;

import java.util.Date;

public class CustomerData extends UserModel {
    private boolean active;
    private String fechaRegistro;
    private String _id;

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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
