package com.pe.delicias.apirest.response.plate;

public class PlateDataResponse {
    private String imagen;
    private String _id;
    private PlateCategoryResponse categoria_id;
    private String nombre;
    private String precio;
    private String descripcion;

    public PlateDataResponse() {

    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public PlateCategoryResponse getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(PlateCategoryResponse categoria_id) {
        this.categoria_id = categoria_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
