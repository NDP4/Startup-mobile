package com.startup.booking_bus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.startup.booking_bus.api.ApiClient;
import com.startup.booking_bus.api.ApiService;
import com.startup.booking_bus.api.BusResponse;
import com.startup.booking_bus.api.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView tvWelcome;
    private RecyclerView rvBuses;
    private ProgressBar progressBar;
    private BusAdapter busAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvWelcome = findViewById(R.id.tv_welcome);
        rvBuses = findViewById(R.id.rv_buses);
        progressBar = findViewById(R.id.progress_bar);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);

        setupRecyclerView();
        setupBottomNavigation(bottomNav);
        loadUserProfile();
        loadBuses();
    }

    private void setupRecyclerView() {
        busAdapter = new BusAdapter();
        rvBuses.setLayoutManager(new LinearLayoutManager(this));
        rvBuses.setAdapter(busAdapter);
    }

    private void setupBottomNavigation(BottomNavigationView bottomNav) {
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_bookings) {
                startActivity(new Intent(MainActivity.this, BookingsActivity.class));
                return true;
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                return true;
            }
            return false;
        });
    }

    private void loadUserProfile() {
        ApiService apiService = ApiClient.getClient(this);
        apiService.getUserProfile().enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserResponse.UserData userData = response.body().getData();
                    String welcomeMessage = String.format(getString(R.string.welcome_message),
                            userData.getName());
                    tvWelcome.setText(welcomeMessage);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadBuses() {
        progressBar.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient(this);
        apiService.getBuses().enqueue(new Callback<BusResponse>() {
            @Override
            public void onResponse(Call<BusResponse> call, Response<BusResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    BusResponse busResponse = response.body();
                    if (busResponse.isSuccess() && busResponse.getData() != null) {
                        // Update BusAdapter with the data list
                        busAdapter.setBuses(busResponse.getData().getData());
                    } else {
                        Toast.makeText(MainActivity.this,
                                busResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this,
                            "Failed to load buses", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BusResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this,
                        "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_home);
    }
}