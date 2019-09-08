package com.pe.delicias.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.pe.delicias.R;
import com.pe.delicias.account.CreateAccountActivity;
import com.pe.delicias.apirest.ApiClient;
import com.pe.delicias.apirest.ApiService;
import com.pe.delicias.apirest.request.CustomerResquest;
import com.pe.delicias.apirest.response.customer.CustomerResponse;
import com.pe.delicias.home.HomeActivity;
import com.pe.delicias.utilities.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private MaterialButton logInMaterialButton;
    private MaterialButton createAccountMaterialButton;
    private TextInputEditText emailTextInputEditText;
    private TextInputEditText passwordTextInputEditText;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        finds();
        events();
    }

    private void finds() {
        logInMaterialButton = findViewById(R.id.log_in_material_button);
        createAccountMaterialButton = findViewById(R.id.create_account_material_button);
        emailTextInputEditText = findViewById(R.id.email_text_input_edit_text);
        passwordTextInputEditText = findViewById(R.id.password_text_input_edit_text);
        progressDialog = new ProgressDialog(this);
    }

    private void events() {
        logInMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateLogIn();
            }
        });

        createAccountMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }

    private void validateLogIn() {
        String email = emailTextInputEditText.getText().toString();
        String password = passwordTextInputEditText.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese su correo electronico", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Utilities.emailIsValid(email)) {
            Toast.makeText(this, "Por favor ingrese un correo electronico valido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese su password", Toast.LENGTH_SHORT).show();
            return;
        }
        logIn(email, password);
    }

    private void logIn(String email, String password) {

        showLoading();

        CustomerResquest customerResquest = new CustomerResquest();
        customerResquest.setEmail(email);
        customerResquest.setPassword(password);

        Call<CustomerResponse> login = ApiClient.getInstance(this).createService(ApiService.class)
                .customerLogIn(customerResquest);

        login.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                /*Log.v("response", "" + response.body().getData().get_id());
                Log.v("response", "" + response.body().getData().getNombres());
                Log.v("response", "" + response.body().getData().getEmail());
                Log.v("response", "" + response.body().getData().getToken());*/
                if (response.isSuccessful()) {
                    hideLoading();
                    goHome();
                } else {
                    hideLoading();
                    showError("No reconocemos tus credenciales, por favor intente nuevamente");
                }
            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {
                hideLoading();
                Log.v("response", "onFailure: " + t.getMessage());
            }
        });
    }

    private void showLoading() {
        progressDialog.setTitle("Iniciando Sesión");
        progressDialog.setMessage("Por favor espere...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void hideLoading() {
        progressDialog.dismiss();
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void goHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
