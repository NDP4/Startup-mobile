package com.startup.booking_bus;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

// BookingsActivity.java
public class BookingsActivity extends AppCompatActivity {
    private RecyclerView rvBookings;
    private ProgressBar progressBar;
    private TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        rvBookings = findViewById(R.id.rv_bookings);
        progressBar = findViewById(R.id.progress_bar);
        tvEmpty = findViewById(R.id.tv_empty);

        // Set up toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Bookings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set up RecyclerView
        rvBookings.setLayoutManager(new LinearLayoutManager(this));
        // TODO: Implement BookingsAdapter

        loadBookings();
    }

    private void loadBookings() {
        progressBar.setVisibility(View.VISIBLE);
        // TODO: Implement API call to get bookings
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}