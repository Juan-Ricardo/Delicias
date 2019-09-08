package com.pe.delicias.category.model;

import java.util.LinkedList;
import java.util.List;

public class Category {
    private String id;
    private String image;
    private String title;
    private String description;

    public Category() {

    }

    public Category(String id, String image, String title, String description) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static List<Category> gets() {
        List<Category> categories = new LinkedList<>();
        categories.add(new Category(
                "1",
                "https://querecetas.net/wp-content/uploads/2019/03/Causa-rellena-de-Pollo.jpg",
                "Entrada",
                "Entrada, contiene Papa, Pollo, Ají, Pimienta"));

        categories.add(new Category(
                "2",
                "https://s3.amazonaws.com/cdn.matadornetwork.com/blogs/2/2015/07/arroz_con_pollo.jpg",
                "Segundo",
                "Contiene Papa, Pollo, Ají, Pimienta"));

        categories.add(new Category(
                "3",
                "https://www.ecestaticos.com/imagestatic/clipping/850/ca4/850ca4f2d62221b11dc7653750623df2/por-que-tomar-sopa-durante-las-comidas-logra-que-adelgaces.jpg",
                "Sopa",
                "Contiene Mousse de Fresa"));

        categories.add(new Category(
                "4",
                "https://i.pinimg.com/originals/61/f2/eb/61f2ebef1e6159202ee33a9c9a5dea89.jpg",
                "Postre",
                "Contiene Mousse de Fresa"));

        categories.add(new Category(
                "5",
                "https://www.vinetur.com/imagenes/2017/octubre/2/vino_comida.jpg",
                "Vino",
                "Vino Blanco, Rojo, Tinto, Rosado, etc."));

        categories.add(new Category(
                "6",
                "https://www.vinetur.com/imagenes/2017/octubre/2/vino_comida.jpg",
                "Platos Marinos",
                "Vino Blanco, Rojo, Tinto, Rosado, etc."));
        return categories;
    }
}
