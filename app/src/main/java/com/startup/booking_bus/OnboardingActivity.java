package com.startup.booking_bus;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;

public class OnboardingActivity extends AppCompatActivity {
    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutDots;
    private MaterialButton btnNext;
    private MaterialButton btnSkip;
    private int[] layouts;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        layoutDots = findViewById(R.id.layoutDots);
        btnNext = findViewById(R.id.btnNext);
        btnSkip = findViewById(R.id.btnSkip);

        // Layout untuk setiap halaman onboarding
        layouts = new int[]{
                R.raw.bus_search,
                R.raw.bus_booking,
                R.raw.bus_animation
        };

        String[] titles = {
                "Cari Bus Pilihan Anda",
                "Pesan Tiket dengan Mudah",
                "Nikmati Perjalanan Anda"
        };

        String[] descriptions = {
                "Temukan berbagai pilihan bus yang sesuai dengan kebutuhan perjalanan Anda",
                "Proses pemesanan yang cepat dan aman dengan berbagai metode pembayaran",
                "Rasakan kenyamanan perjalanan dengan armada bus berkualitas"
        };

        onboardingAdapter = new OnboardingAdapter(layouts, titles, descriptions);
        viewPager.setAdapter(onboardingAdapter);
        viewPager.registerOnPageChangeCallback(pageChangeCallback);

        btnSkip.setOnClickListener(v -> launchLoginScreen());
        btnNext.setOnClickListener(v -> {
            int current = viewPager.getCurrentItem();
            if (current < layouts.length - 1) {
                viewPager.setCurrentItem(current + 1);
            } else {
                launchLoginScreen();
            }
        });

        addBottomDots(0);
    }

    private void addBottomDots(int currentPage) {
        dots = new ImageView[layouts.length];
        layoutDots.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int size = 12;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(size, 0, size, 0);
            dots[i].setLayoutParams(params);
            dots[i].setImageDrawable(ContextCompat.getDrawable(this,
                    i == currentPage ? R.drawable.dot_active : R.drawable.dot_inactive));
            layoutDots.addView(dots[i]);
        }
    }

    private ViewPager2.OnPageChangeCallback pageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            btnNext.setText(position == layouts.length - 1 ? "Mulai" : "Lanjut");
        }
    };

    private void launchLoginScreen() {
        startActivity(new Intent(OnboardingActivity.this, LoginActivity.class));
        finish();
    }
}