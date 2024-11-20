package com.example.t1travelexpertsandroid;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RewardActivity extends AppCompatActivity {
    private EditText rewardId, rewardName, rewardDescription;
    private Button btnAccept, btnDelete, btnCancel;
    private Reward reward = null;

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
            rewardId.setVisibility(View.GONE);
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

    //UPDATE
    private void saveOrUpdateItem() {
    try {
        String mode = "create";

        //Get the updated fields from the frontend
        String name = rewardName.getText().toString().trim();
        String description = rewardDescription.getText().toString().trim();
        int id = Integer.parseInt(rewardId.getText().toString().trim());

        //validate we had a non null name for the reward ~ descriptions can be null
        if (name.isEmpty()) {
            Log.e("ERROR", "Reward Name is Required!");
            Toast.makeText(RewardActivity.this, "Reward Name is Required!" , Toast.LENGTH_SHORT).show();
            return;
        }

        //Make sure a reward exists first
        if (reward == null){
            reward = new Reward(id, name, description);
        }

        //update the current reward with the new values received
        reward.setId(id);
        reward.setName(name);
        reward.setDescription(description);

        Log.d("Reward Data", "Name: " + reward.getName() + " Description: " + reward.getDescription());

        //Create json object to send
        JSONObject rewardJson = new JSONObject();
        String method = reward == null ? "post" : "put";

        //only add the id if we are updating an entry
        if (method.equals("put")) rewardJson.put("id", reward.getId());

        //add reward name and reward description for both methods
        rewardJson.put("rwdName", reward.getName());
        rewardJson.put("rwdDesc", reward.getDescription());

        Log.d("Reward JSON", rewardJson.toString());

        //make the put request to the rest api
        OkHttpClient client = new OkHttpClient();

        String url = "http://10.0.2.2:8080/TeamOneREST_war_exploded/api/rewards/" + method;
        RequestBody body = RequestBody.create(rewardJson.toString(), MediaType.parse("application/json"));

        //create the request
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("API ERROR", "Update Failed! " + e);
                Toast.makeText(RewardActivity.this, "Error Updating Reward!" , Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                //This returns json with only the id field ??? idk whats going on
                String responseBody = response.body().string();
                Log.d("API RESPONSE", responseBody);

                if (response.isSuccessful()) {
                    //Successfully updated the activity
                    Log.e("API SUCCESS", "Updated Success!");
                    runOnUiThread(() ->
                    {
                        //Update the front end here with updated data
                        Toast.makeText(RewardActivity.this, "Reward Updated", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                } else {
                    //Update Failed
                    Log.e("API ERROR", "Updated Failed! " + response.message());
                    Toast.makeText(RewardActivity.this, "Error Updating Reward!" , Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        } catch(Exception e) {
            Log.e("ERROR", "Error Updating/Saving");
            Toast.makeText(RewardActivity.this, "Error Updating Reward!" , Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    //DELETE
    private void deleteItem() {
        //check we have a non null reward object
        if (reward == null) return;

        //get the reward id
        int rewardID = reward.getId();
        Log.d("DELETE", "Deleting Reward #" + rewardID);

        //send DELETE request to rest api
        OkHttpClient client = new OkHttpClient();
        String url = "http://10.0.2.2:8080/TeamOneREST_war_exploded/api/rewards/delete/" + rewardID;

        //create the request
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("ERROR", "Error deleting entry! " + e);
                Toast.makeText(RewardActivity.this, "Error Deleting Reward! Foreign Key Restraint?" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                String responseBody = response.body().string();
                Log.d("DELETE RESPONSE", responseBody);

                //validate we get a response from the server
                if (response.isSuccessful() == false){
                    Log.e("ERROR", "Error Deleting entry! " + response.message());
                    Toast.makeText(RewardActivity.this, "Error Deleting Reward!" , Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.e("DELETE", "Delete Success!");
                runOnUiThread(() -> {
                    Toast.makeText(RewardActivity.this, "Reward #" + rewardID + " Deleted" , Toast.LENGTH_SHORT).show();
                    finish();  // Close the activity
                });
            }
        });

    }
}