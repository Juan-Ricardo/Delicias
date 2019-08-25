package com.pe.delicias.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.pe.delicias.R;
import com.pe.delicias.apirest.ApiClient;
import com.pe.delicias.apirest.ApiService;
import com.pe.delicias.apirest.request.UserRequest;
import com.pe.delicias.apirest.response.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("kelvin.ca91@gmail.com");
        userRequest.setPassword("123456");

        Call<UserResponse> login = ApiClient.getInstance(this).createService(ApiService.class)
                .login(userRequest);
        login.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.isSuccessful()){
                    UserResponse body = response.body();
                    Log.v("login: ", "" + body.getData().getNombres());
                    Log.v("login: ", "" + body.getData().getToken());
                }else{
                    Log.v("login: ", "No existe este usuario");
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.v("login: ", "" + t.getMessage());
            }
        });

    }
}
