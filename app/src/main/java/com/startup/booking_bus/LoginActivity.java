package com.startup.booking_bus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.startup.booking_bus.api.ApiClient;
import com.startup.booking_bus.api.ApiService;
import com.startup.booking_bus.api.LoginRequest;
import com.startup.booking_bus.api.LoginResponse;
import com.startup.booking_bus.utils.SessionManager;
import com.tapadoo.alerter.Alerter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText etEmail, etPassword;
    private View headerContainer;
    private TextView tvSwipeHint;
    private LottieAnimationView swipeAnimation;
    private float originalY;
    private MaterialButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        headerContainer = findViewById(R.id.headerContainer);
        tvSwipeHint = findViewById(R.id.tvSwipeHint);
        swipeAnimation = findViewById(R.id.swipeAnimation);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        View bottomSheet = findViewById(R.id.bottomSheet);

        // Store original position
        headerContainer.post(() -> originalY = headerContainer.getY());

        // Setup animation
        swipeAnimation.setSpeed(1.5f);
        swipeAnimation.playAnimation();

        // Setup bottom sheet behavior
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {}

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // Move and scale welcome text
                float targetY = originalY - (originalY * 0.7f * slideOffset);
                float targetScale = 1 - (0.2f * slideOffset);
                headerContainer.setY(targetY);
                headerContainer.setScaleX(targetScale);
                headerContainer.setScaleY(targetScale);

                // Fade out swipe hint and animation
                tvSwipeHint.setAlpha(1 - slideOffset);
                swipeAnimation.setAlpha(1 - slideOffset);
            }
        });

        btnLogin.setOnClickListener(v -> validateAndLogin());
    }

    private void validateAndLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validation
        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Please fill all fields", false);
            return;
        }

        // Show progress
        btnLogin.setEnabled(false);

        // Create login request
        LoginRequest loginRequest = new LoginRequest(email, password);
        ApiService apiService = ApiClient.getClient(this);

        apiService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                btnLogin.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.isSuccess()) {
                        // Save session using SessionManager
                        SessionManager sessionManager = new SessionManager(LoginActivity.this);
                        sessionManager.saveAuthSession(
                                loginResponse.getData().getToken(),
                                loginResponse.getData().getUser(),
                                loginResponse.getData().getEmail()
                        );

                        showAlert("Login successful", true);

                        // Navigate to main activity
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finishAffinity();
                    } else {
                        showAlert(loginResponse.getMessage(), false);
                    }
                } else {
                    showAlert("Login failed", false);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                btnLogin.setEnabled(true);
                showAlert("Network error: " + t.getMessage(), false);
            }
        });
    }

    private void showAlert(String message, boolean isSuccess) {
        int backgroundColor = isSuccess ? R.color.available : R.color.booked;

        Alerter.create(this)
                .setText(message)
                .setBackgroundColorRes(backgroundColor)
                .setIcon(isSuccess ? R.drawable.ic_success : R.drawable.ic_error)
                .setDuration(2000)
                .enableSwipeToDismiss()
                .show();
    }

    public void navigateToRegister(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}