package com.startup.booking_bus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable; // Add this import

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder> {
    private int[] layouts;
    private String[] titles;
    private String[] descriptions;

    public OnboardingAdapter(int[] layouts, String[] titles, String[] descriptions) {
        this.layouts = layouts;
        this.titles = titles;
        this.descriptions = descriptions;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_onboarding, parent, false);
        return new OnboardingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        holder.animationView.setAnimation(layouts[position]);
        holder.animationView.setRepeatCount(LottieDrawable.INFINITE);
        holder.animationView.playAnimation();
        holder.tvTitle.setText(titles[position]);
        holder.tvDescription.setText(descriptions[position]);
    }

    @Override
    public int getItemCount() {
        return layouts.length;
    }

    static class OnboardingViewHolder extends RecyclerView.ViewHolder {
        LottieAnimationView animationView;
        TextView tvTitle;
        TextView tvDescription;

        OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            animationView = itemView.findViewById(R.id.animationView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }
    }
}