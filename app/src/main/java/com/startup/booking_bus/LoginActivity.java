package com.startup.booking_bus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.startup.booking_bus.api.ApiClient;
import com.startup.booking_bus.api.ApiService;
import com.startup.booking_bus.api.LoginRequest;
import com.startup.booking_bus.api.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText etEmail, etPassword;
    private MaterialCheckBox cbRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        cbRemember = findViewById(R.id.cb_remember);
        MaterialButton btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> validateAndLogin());
    }

    private void validateAndLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show progress
        findViewById(R.id.btn_login).setEnabled(false);

        // Create login request
        LoginRequest loginRequest = new LoginRequest(email, password);
        ApiService apiService = ApiClient.getClient(this);

        apiService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                findViewById(R.id.btn_login).setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.isSuccess()) {
                        // Save token in SharedPreferences
                        SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("token", loginResponse.getData().getToken());
                        editor.apply();

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finishAffinity();
                    } else {
                        Toast.makeText(LoginActivity.this,
                                loginResponse.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Login failed",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                findViewById(R.id.btn_login).setEnabled(true);
                Toast.makeText(LoginActivity.this,
                        "Network error",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void navigateToRegister(android.view.View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}