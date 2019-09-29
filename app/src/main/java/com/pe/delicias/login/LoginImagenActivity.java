package com.pe.delicias.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.pe.delicias.R;
import com.pe.delicias.account.CreateAccountActivity;
import com.pe.delicias.apirest.ApiClient;
import com.pe.delicias.apirest.ApiService;
import com.pe.delicias.apirest.request.customer.CustomerResquest;
import com.pe.delicias.apirest.response.customer.CustomerResponse;
import com.pe.delicias.home.HomeActivity;
import com.pe.delicias.utilities.PreferencesSingleton;
import com.pe.delicias.utilities.Utilities;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginImagenActivity extends AppCompatActivity {

    private static final int SIGN_IN_GOOGLE = 100;
    private static final String TAG = "Login";
    private TextView versionAppTextView;
    private TextView titleLogInTextView;
    private MaterialButton logInMaterialButton;
    private MaterialButton createAccountMaterialButton;
    private TextInputEditText emailTextInputEditText;
    private TextInputEditText passwordTextInputEditText;
    private TextView dontHaveAccountTextView;
    private TextView signUpTextView;
    private ProgressDialog progressDialog;
    private SignInButton signInGoogleButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_imagen);
        finds();
        events();
    }

    private void finds() {

        versionAppTextView = findViewById(R.id.version_app_text_view);
        versionAppTextView.setTypeface(Utilities.sansBold(this));

        titleLogInTextView = findViewById(R.id.title_log_in_text_view);
        titleLogInTextView.setTypeface(Utilities.sansBlack(this));

        logInMaterialButton = findViewById(R.id.log_in_material_button);
        createAccountMaterialButton = findViewById(R.id.create_account_material_button);
        createAccountMaterialButton.setTypeface(Utilities.sansLight(this));

        emailTextInputEditText = findViewById(R.id.email_text_input_edit_text);
        emailTextInputEditText.setTypeface(Utilities.sansLight(this));

        passwordTextInputEditText = findViewById(R.id.password_text_input_edit_text);
        passwordTextInputEditText.setTypeface(Utilities.sansLight(this));

        dontHaveAccountTextView = findViewById(R.id.dont_have_account);
        dontHaveAccountTextView.setTypeface(Utilities.sansLight(this));

        signUpTextView = findViewById(R.id.sign_up_text_view);
        signUpTextView.setTypeface(Utilities.sansBold(this));

        progressDialog = new ProgressDialog(this);

        signInGoogleButton = findViewById(R.id.sign_in_google);
        signInGoogleButton.setSize(SignInButton.SIZE_STANDARD);
        signInGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInGoogle();
            }
        });
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
                Intent intent = new Intent(LoginImagenActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginImagenActivity.this, CreateAccountActivity.class);
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
                    savePreferences(response);
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

    private void savePreferences(Response<CustomerResponse> response) {
        String id = response.body().getData().get_id();
        String token = response.body().getData().getToken();
        String names = response.body().getData().getNombres();

        PreferencesSingleton.getInstance(getBaseContext()).save(Utilities.ID_CUSTOMER, id);
        PreferencesSingleton.getInstance(getBaseContext()).save(Utilities.TOKEN_CUSTOMER, token);
        PreferencesSingleton.getInstance(getBaseContext()).save(Utilities.NAMES_CUSTOMER, names);
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

    private void signInGoogle() {

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, SIGN_IN_GOOGLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_GOOGLE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        //95:BD:4B:61:B3:8E:87:07:A1:BE:4D:9C:9C:10:4B:A3:84:FA:E4:14 - Ricardo
        //36:FC:C2:D3:6C:00:4E:94:77:1A:1A:EC:A7:E4:1F:34:CD:1F:9F:56 - Yesenia

        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getBaseContext(), "Correctamente", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
