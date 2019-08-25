package com.pe.delicias.apirest;

import com.pe.delicias.apirest.request.UserRequest;
import com.pe.delicias.apirest.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/auth/usuarios-login")
    Call<UserResponse> login(@Body UserRequest userRequest);
}
