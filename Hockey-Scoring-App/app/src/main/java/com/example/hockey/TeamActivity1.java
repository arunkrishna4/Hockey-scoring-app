package com.example.hockey;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TeamActivity1 extends AppCompatActivity {

    private EditText teamNameInput;
    private RecyclerView playerRecyclerView;
    private PlayerAdapter playerAdapter;
    private ArrayList<Player> playerList;
    private TextView playerCountText;
    private ActivityResultLauncher<Intent> addPlayerLauncher;
    private int teamCardPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_team_details1);

        teamNameInput = findViewById(R.id.teamNameInput);
        ImageView backBtn = findViewById(R.id.backBtn);
        Button addPlayerBtn = findViewById(R.id.addPlayerBtn);
        playerCountText = findViewById(R.id.playerCountText);

        // RecyclerView setup
        playerRecyclerView = findViewById(R.id.playerRecyclerView);
        playerList = new ArrayList<>();
        playerAdapter = new PlayerAdapter(playerList);

        // 🔹 Update player count when deleted
        playerAdapter.setOnPlayerDeleteListener(() ->
                playerCountText.setText("Players: " + playerList.size())
        );

        playerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        playerRecyclerView.setAdapter(playerAdapter);

        // Team name from intent
        String currentName = getIntent().getStringExtra("teamName");
        teamCardPosition = getIntent().getIntExtra("position", -1);
        if (currentName != null) {
            teamNameInput.setText(currentName);
        }

        // Forwarded player from TeamActivity
        if (getIntent().hasExtra("playerName")) {
            String name = getIntent().getStringExtra("playerName");
            String jersey = getIntent().getStringExtra("jerseyNo");
            String positionStr = getIntent().getStringExtra("position");
            playerList.add(new Player(name, jersey, positionStr));
            playerAdapter.notifyDataSetChanged();
        }

        playerCountText.setText("Players: " + playerList.size());

        // Back button
        backBtn.setOnClickListener(v -> {
            String updatedName = teamNameInput.getText().toString().trim();
            Intent resultIntent = new Intent();
            resultIntent.putExtra("teamName", updatedName);
            resultIntent.putExtra("position", teamCardPosition);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });

        // Add Player
        addPlayerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        String name = result.getData().getStringExtra("playerName");
                        String jersey = result.getData().getStringExtra("jerseyNo");
                        String positionStr = result.getData().getStringExtra("position");

                        playerList.add(new Player(name, jersey, positionStr));
                        playerAdapter.notifyItemInserted(playerList.size() - 1);
                        playerCountText.setText("Players: " + playerList.size());
                    }
                }
        );

        addPlayerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(TeamActivity1.this, AddPlayer.class);
            addPlayerLauncher.launch(intent);
        });
    }
}
