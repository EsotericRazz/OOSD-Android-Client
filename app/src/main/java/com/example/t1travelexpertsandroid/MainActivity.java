package com.example.t1travelexpertsandroid;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RewardAdapter adapter;
    private List<Reward> rewardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sample data for testing; replace with actual API data
        rewardList = new ArrayList<>();
        rewardList.add(new Reward(1, "Sample Reward 1"));
        rewardList.add(new Reward(2, "Sample Reward 2"));

        // Setup adapter with click listener
        adapter = new RewardAdapter(rewardList, this::openDetailActivity);
        recyclerView.setAdapter(adapter);

        // Floating Action Button for adding a new reward
        FloatingActionButton actionButton = findViewById(R.id.addReward);
        actionButton.setOnClickListener(v -> openDetailActivity(null));

        fetchDataFromApi();
    }

    private void fetchDataFromApi() {
        /* TODO: Fetch data from API */
        // fetch data from API, update rewardList, and call adapter.notifyDataSetChanged();
        try {

        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    private void openDetailActivity(Reward reward) {
        Intent intent = new Intent(this, RewardActivity.class);
        if (reward != null) {
            intent.putExtra("rewardData", reward);
        }
        startActivity(intent);
    }
}