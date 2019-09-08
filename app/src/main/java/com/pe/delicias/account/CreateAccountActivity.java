package com.pe.delicias.account;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.pe.delicias.R;
import com.pe.delicias.apirest.ApiClient;
import com.pe.delicias.apirest.ApiService;
import com.pe.delicias.apirest.request.customer.CustomerResquest;
import com.pe.delicias.apirest.response.customer.CustomerResponse;
import com.pe.delicias.utilities.Utilities;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccountActivity extends AppCompatActivity {

    @BindView(R.id.name_text_input_edit_text)
    TextInputEditText nameTextInputEditText;

    @BindView(R.id.paternal_text_input_edit_text)
    TextInputEditText paternalTextInputEditText;

    @BindView(R.id.maternal_text_input_edit_text)
    TextInputEditText maternalTextInputEditText;

    @BindView(R.id.email_text_input_edit_text)
    TextInputEditText emailTextInputEditText;

    @BindView(R.id.password_text_input_edit_text)
    TextInputEditText passwordTextInputEditText;

    @BindView(R.id.password_confirm_text_input_edit_text)
    TextInputEditText passwordConfirmTextInputEditText;

    @BindView(R.id.create_account_material_button)
    MaterialButton createAccountMaterialButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);
    }

    private void validate() {
        String name = nameTextInputEditText.getText().toString();
        String paternal = paternalTextInputEditText.getText().toString();
        String maternal = maternalTextInputEditText.getText().toString();
        String email = emailTextInputEditText.getText().toString();
        String password = passwordTextInputEditText.getText().toString();
        String passwordConfirm = passwordConfirmTextInputEditText.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese su nombre", Toast.LENGTH_SHORT).show();
            return;
        }

        if (paternal.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese su apellido paterno", Toast.LENGTH_SHORT).show();
            return;
        }

        if (maternal.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese su apellido materno", Toast.LENGTH_SHORT).show();
            return;
        }

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

        if (passwordConfirm.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese su password de confirmación", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equalsIgnoreCase(passwordConfirm)) {
            Toast.makeText(this, "Las contrasenias no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }
        save(name, paternal, maternal, email, password);
    }

    private void save(String name, String paternal, String maternal, String email, String password) {

        CustomerResquest customerResquest = new CustomerResquest();
        customerResquest.setNombres(name);
        customerResquest.setApellido_paterno(paternal);
        customerResquest.setApellido_materno(maternal);
        customerResquest.setImagen("default.jpg");
        customerResquest.setEmail(email);
        customerResquest.setPassword(password);

        Call<CustomerResponse> createAccount = ApiClient.getInstance(this)
                .createService(ApiService.class)
                .createAccount(customerResquest);
        createAccount.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                Log.v("response", "" + response.body().getData().get_id());
                Log.v("response", "" + response.body().getData().getNombres());
                Log.v("response", "" + response.body().getData().getEmail());
                Log.v("response", "" + response.body().getData().getFechaRegistro());
            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {
                Log.v("response", "onFailure: " + t.getMessage());
            }
        });
        //Toast.makeText(this, "Correctamente!", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.create_account_material_button)
    public void createAccount() {
        //Toast.makeText(this, "Creando tu cuenta...", Toast.LENGTH_LONG).show();
        validate();
    }

}
