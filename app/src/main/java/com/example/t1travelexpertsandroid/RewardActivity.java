package com.example.t1travelexpertsandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RewardActivity extends AppCompatActivity {
    private EditText itemName;
    private Button btnAccept, btnDelete;
    private Reward reward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        itemName = findViewById(R.id.txtRewardName);
        btnAccept = findViewById(R.id.btnAccept);
        btnDelete = findViewById(R.id.btnDelete);

        reward = getIntent().getParcelableExtra("rewardData");

        if (reward != null) {
            populateFields(reward);
            btnDelete.setVisibility(View.VISIBLE);
        } else {
            btnDelete.setVisibility(View.GONE);
        }

        btnAccept.setOnClickListener(v -> saveOrUpdateItem());
        btnDelete.setOnClickListener(v -> deleteItem());
    }

    private void populateFields(Reward reward) {
        itemName.setText(reward.getName());
    }

    private void saveOrUpdateItem() {
        // Logic to save or update the reward
        finish();
    }

    private void deleteItem() {
        if (reward != null) {
            // Logic to delete the reward
            finish();
        }
    }
}