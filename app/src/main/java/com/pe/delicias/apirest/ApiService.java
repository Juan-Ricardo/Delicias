package com.pe.delicias.apirest;

import com.pe.delicias.apirest.request.customer.CustomerResquest;
import com.pe.delicias.apirest.request.user.UserRequest;
import com.pe.delicias.apirest.response.category.CategoryResponse;
import com.pe.delicias.apirest.response.customer.CustomerResponse;
import com.pe.delicias.apirest.response.user.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/auth/usuarios-login")
    Call<UserResponse> login(@Body UserRequest userRequest);

    @POST("/clientes")
    Call<CustomerResponse> createAccount(@Body CustomerResquest customerResquest);

    @POST("/auth/clientes-login")
    Call<CustomerResponse> customerLogIn(@Body CustomerResquest customerResquest);

    @GET("/categorias")
    Call<CategoryResponse> getCategories();

}
