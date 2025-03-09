package com.startup.booking_bus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText etEmail, etPassword;
    private MaterialCheckBox cbRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        cbRemember = findViewById(R.id.cb_remember);
        MaterialButton btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> validateLogin());
    }

    private void validateLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty()) {
            etEmail.setError("Email required");
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Password required");
            return;
        }

        performLogin(email, password);
    }

    public void navigateToRegister(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void performLogin(String email, String password) {
        // Implement login logic here
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}