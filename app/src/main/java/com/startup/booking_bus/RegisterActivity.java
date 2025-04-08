package com.startup.booking_bus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.startup.booking_bus.api.ApiClient;
import com.startup.booking_bus.api.ApiService;
import com.startup.booking_bus.api.RegisterRequest;
import com.startup.booking_bus.api.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText etName, etEmail, etPhone, etPassword, etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize views
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        MaterialButton btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(v -> validateAndRegister());
    }

    private void validateAndRegister() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // Validation
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() ||
                password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show progress
        findViewById(R.id.btn_register).setEnabled(false);

        // Create register request
        RegisterRequest registerRequest = new RegisterRequest(name, email, password, phone);
        ApiService apiService = ApiClient.getClient(this);

        apiService.register(registerRequest).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                findViewById(R.id.btn_register).setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    RegisterResponse registerResponse = response.body();
                    if (registerResponse.isSuccess()) {
                        // Save auth token
                        SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("token", registerResponse.getData().getToken());
                        editor.apply();

                        // Navigate to main activity
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finishAffinity();
                    } else {
                        Toast.makeText(RegisterActivity.this,
                                registerResponse.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this,
                            "Registration failed",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                findViewById(R.id.btn_register).setEnabled(true);
                Toast.makeText(RegisterActivity.this,
                        "Network error",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void navigateToLogin(View view) {
        finish();
    }
}