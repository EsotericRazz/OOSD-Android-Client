package com.example.t1travelexpertsandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.ViewHolder> {
    private final List<Reward> items; // list of rewards
    private final OnItemClickListener listener; // on click listener (for each item)

    // Constructor
    public RewardAdapter(List<Reward> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    // Tells the adapter which view to use for each item in the recycler view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reward_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    // gets count
    @Override
    public int getItemCount() {
        return items.size();
    }

    // ViewHolder class
    class ViewHolder extends RecyclerView.ViewHolder {
        // Text views within item view
        TextView rewardName;
        TextView rewardDescription;

        ViewHolder(View itemView) {
            super(itemView);
            // Binds the text views
            rewardName = itemView.findViewById(R.id.txtRewardName);
            rewardDescription = itemView.findViewById(R.id.txtRewardDesc);

            // Whenever an item is clicked, get it's position and open the view
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(items.get(position));
                }
            });
        }

        // binds the data
        void bind(Reward item) {
            rewardName.setText(item.getName());
            rewardDescription.setText(item.getDescription());
        }
    }

    // Define a custom interface that passes a Reward item
    public interface OnItemClickListener {
        void onItemClick(Reward item);
    }
}