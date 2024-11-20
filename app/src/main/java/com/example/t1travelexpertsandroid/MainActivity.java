package com.example.t1travelexpertsandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
        rewardList.add(new Reward(1, "Sample Reward 1", "Blah blah blah"));
        rewardList.add(new Reward(2, "Sample Reward 2", "Blah blah blah"));

        // Setup adapter with click listener
        adapter = new RewardAdapter(rewardList, this::openDetailActivity);
        recyclerView.setAdapter(adapter);

        // Floating Action Button for adding a new reward
        FloatingActionButton actionButton = findViewById(R.id.addReward);
        actionButton.setOnClickListener(v -> openDetailActivity(null));

        fetchDataFromApi();
    }

    private void fetchDataFromApi() {
        OkHttpClient client = new OkHttpClient();
        String url = "http://10.0.2.2:8080/TeamOneREST_war_exploded/api/rewards";

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("API_ERROR", "Request failed: " + e.getMessage());
                System.out.println(e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {

                    Gson gson = new Gson();
                    JsonArray jsonArray = gson.fromJson(response.body().string(), JsonArray.class);
                    Log.d("API_RESPONSE", "Received data: " + jsonArray);

                    rewardList.clear();
                    jsonArray.forEach(jsonElement -> {
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        int id = jsonObject.get("id").getAsInt();
                        String name = jsonObject.get("rwdName").getAsString();
                        String desc = jsonObject.get("rwdDesc") != null ? jsonObject.get("rwdDesc").getAsString(): "No Description available";

                        Reward reward = new Reward(id, name, desc);
                        rewardList.add(reward);
                    });

                    // Update adapter on the UI thread
                    runOnUiThread(() -> adapter.notifyDataSetChanged());
                } else {
                    Log.e("API_ERROR", "Unsuccessful response or null body");
                    System.out.println("Response was not successful or body was null.");
                }
            }
        });
    }

    private final ActivityResultLauncher<Intent> detailActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    rewardList.clear();
                    fetchDataFromApi();
                }
            }
    );

    private void openDetailActivity(Reward reward) {
        Intent intent = new Intent(this, RewardActivity.class);
        if (reward != null) {
            intent.putExtra("rewardData", reward);
        }
        detailActivityLauncher.launch(intent);
    }
}