package com.pe.delicias.apirest.response.category;

import java.util.List;

public class CategoryResponse {
    private String message;
    private List<CategoryData> data;

    public CategoryResponse() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CategoryData> getData() {
        return data;
    }

    public void setData(List<CategoryData> data) {
        this.data = data;
    }
}
