package com.startup.booking_bus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.startup.booking_bus.api.ApiClient;
import com.startup.booking_bus.api.ApiService;
import com.startup.booking_bus.api.LogoutResponse;
import com.startup.booking_bus.api.UserResponse;
import com.startup.booking_bus.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private TextView tvName, tvEmail, tvPhone;
    private View progressBar;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        tvName = findViewById(R.id.tv_name);
        tvEmail = findViewById(R.id.tv_email);
        tvPhone = findViewById(R.id.tv_phone);
        progressBar = findViewById(R.id.progress_bar);
        MaterialButton btnLogout = findViewById(R.id.btn_logout);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Profile");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize SharedPreferences
        prefs = getSharedPreferences("auth", MODE_PRIVATE);

        // Set up logout button
        btnLogout.setOnClickListener(v -> handleLogout());

        // Load user profile
        loadUserProfile();
    }

    private void loadUserProfile() {
        progressBar.setVisibility(View.VISIBLE);

        ApiService apiService = ApiClient.getClient(this);
        apiService.getUserProfile().enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    UserResponse userResponse = response.body();
                    if (userResponse.isSuccess()) {
                        UserResponse.UserData userData = userResponse.getData();
                        tvName.setText(userData.getName());
                        tvEmail.setText(userData.getEmail());
                        tvPhone.setText(userData.getPhone());
                    } else {
                        showError("Failed to load profile: " + userResponse.getMessage());
                    }
                } else {
                    showError("Failed to load profile");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                showError("Network error: " + t.getMessage());
            }
        });
    }

    private void handleLogout() {
        ApiService apiService = ApiClient.getClient(this);
        apiService.logout().enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                // Clear session
                SessionManager sessionManager = new SessionManager(ProfileActivity.this);
                sessionManager.clearSession();

                // Navigate to login screen
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                showError("Network error: " + t.getMessage());
            }
        });
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}