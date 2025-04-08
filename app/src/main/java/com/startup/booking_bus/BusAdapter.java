package com.startup.booking_bus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.startup.booking_bus.model.Bus;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.BusViewHolder> {

    private List<Bus> buses = new ArrayList<>();
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    @NonNull
    @Override
    public BusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bus, parent, false);
        return new BusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusViewHolder holder, int position) {
        Bus bus = buses.get(position);
        holder.tvBusName.setText(bus.getName());
        holder.tvBusPlate.setText(bus.getNumberPlate());
        holder.tvBusDescription.setText(bus.getDescription());
        holder.tvBusCapacity.setText(String.valueOf(bus.getDefaultSeatCapacity()));

        // Set price based on pricing type
        String price = getPriceText(bus);
        holder.tvBusPrice.setText(price);

        // Set status chip
        holder.chipStatus.setText(getStatusText(bus.getStatus()));
        holder.chipStatus.setChipBackgroundColorResource(getStatusColor(bus.getStatus()));

        // Set pricing type chip
        holder.chipPricing.setText(getPricingTypeText(bus.getPricingType()));

        // Load bus image
        if (bus.getImage() != null && !bus.getImage().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(bus.getImage())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.ivBusImage);
        } else {
            holder.ivBusImage.setImageResource(R.drawable.ic_launcher_background);
        }
    }

    @Override
    public int getItemCount() {
        return buses.size();
    }

    public void setBuses(List<Bus> buses) {
        this.buses = buses != null ? buses : new ArrayList<>();
        notifyDataSetChanged();
    }

    private String getPriceText(Bus bus) {
        if (bus.getPricingType() == null) return "";

        switch (bus.getPricingType().toLowerCase()) {
            case "daily":
                return currencyFormat.format(bus.getPricePerDay()) + " / hari";
            case "distance":
                return currencyFormat.format(bus.getPricePerKm()) + " / km";
            default:
                return "";
        }
    }

    private String getPricingTypeText(String pricingType) {
        if (pricingType == null) return "";

        switch (pricingType.toLowerCase()) {
            case "daily":
                return "Harian";
            case "distance":
                return "Per KM";
            default:
                return "";
        }
    }

    private String getStatusText(String status) {
        if (status == null) return "Tersedia";

        switch (status.toLowerCase()) {
            case "available":
                return "Tersedia";
            case "maintenance":
                return "Dalam Perbaikan";
            case "booked":
                return "Dibooking";
            default:
                return "Tersedia";
        }
    }

    private int getStatusColor(String status) {
        if (status == null) return R.color.available;

        switch (status.toLowerCase()) {
            case "available":
                return R.color.available;
            case "maintenance":
                return R.color.maintenance;
            case "booked":
                return R.color.booked;
            default:
                return R.color.available;
        }
    }

    static class BusViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBusImage;
        TextView tvBusName;
        TextView tvBusPlate;
        TextView tvBusDescription;
        TextView tvBusCapacity;
        TextView tvBusPrice;
        Chip chipStatus;
        Chip chipPricing;

        BusViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBusImage = itemView.findViewById(R.id.iv_bus_image);
            tvBusName = itemView.findViewById(R.id.tv_bus_name);
            tvBusPlate = itemView.findViewById(R.id.tv_bus_plate);
            tvBusDescription = itemView.findViewById(R.id.tv_bus_description);
            tvBusCapacity = itemView.findViewById(R.id.tv_bus_capacity);
            tvBusPrice = itemView.findViewById(R.id.tv_bus_price);
            chipStatus = itemView.findViewById(R.id.chip_status);
            chipPricing = itemView.findViewById(R.id.chip_pricing);
        }
    }
}