package com.startup.booking_bus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText etFullname, etEmail, etPassword, etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFullname = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        MaterialButton btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(v -> validateRegistration());
    }

    private void validateRegistration() {
        if (etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
            // Process registration
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            etConfirmPassword.setError("Password tidak cocok");
        }
    }

    public void navigateToLogin(View view) {
        finish();
    }
}