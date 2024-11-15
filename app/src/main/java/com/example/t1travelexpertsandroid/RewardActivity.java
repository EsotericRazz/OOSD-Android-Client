package com.example.t1travelexpertsandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RewardActivity extends AppCompatActivity {
    private EditText rewardId, rewardName, rewardDescription;
    private Button btnAccept, btnDelete, btnCancel;
    private Reward reward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        rewardId = findViewById(R.id.txtRewardID);
        rewardName = findViewById(R.id.txtRewardName);
        rewardDescription = findViewById(R.id.txtRewardDesc);

        btnAccept = findViewById(R.id.btnAccept);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);

        reward = getIntent().getParcelableExtra("rewardData");

        if (reward != null) {
            populateFields(reward);
            btnDelete.setVisibility(View.VISIBLE);
        } else {
            btnDelete.setVisibility(View.GONE);
        }

        btnAccept.setOnClickListener(v -> saveOrUpdateItem());
        btnDelete.setOnClickListener(v -> deleteItem());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void populateFields(Reward reward) {
        rewardId.setText(String.valueOf(reward.getId()));
        rewardName.setText(reward.getName());
        rewardDescription.setText(reward.getDescription());
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