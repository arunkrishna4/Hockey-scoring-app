package com.example.hockey;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;

public class TeamActivity extends AppCompatActivity {

    private EditText teamNameInput;
    private static final int EDIT_TEAM_REQUEST = 101;

    private ActivityResultLauncher<Intent> addPlayerLauncher; // NEW

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_team_details);

        teamNameInput = findViewById(R.id.teamNameInput);
        ImageView backBtn = findViewById(R.id.backBtn);
        ImageView editTeamBtn = findViewById(R.id.editTeamBtn);
        Button addPlayerBtn = findViewById(R.id.addPlayerBtn);

        // Register launcher for AddPlayer (NEW)
        addPlayerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        // Forward player details to TeamActivity1
                        Intent intent = new Intent(TeamActivity.this, TeamActivity1.class);
                        intent.putExtra("teamName", teamNameInput.getText().toString().trim());
                        intent.putExtras(result.getData().getExtras()); // pass player details
                        startActivity(intent);
                    }
                }
        );

        // Set predefined team name
        String predefinedName = getIntent().getStringExtra("teamName");
        if (predefinedName != null) {
            teamNameInput.setText(predefinedName);
            teamNameInput.setEnabled(false);
        } else {
            teamNameInput.setText("Team A");
            teamNameInput.setEnabled(false);
        }

        // Back → return team name to MainActivity
        backBtn.setOnClickListener(v -> {
            String teamName = teamNameInput.getText().toString().trim();
            Intent resultIntent = new Intent();
            if (!teamName.isEmpty()) {
                resultIntent.putExtra("teamName", teamName);
                setResult(Activity.RESULT_OK, resultIntent);
            } else {
                setResult(Activity.RESULT_CANCELED);
            }
            finish();
        });

        // Edit pencil → open TeamActivity1
        editTeamBtn.setOnClickListener(v -> {
            String currentName = teamNameInput.getText().toString().trim();
            Intent intent = new Intent(TeamActivity.this, TeamActivity1.class);
            intent.putExtra("teamName", currentName);
            startActivityForResult(intent, EDIT_TEAM_REQUEST);
        });

        // Add Player → open AddPlayer with launcher
        addPlayerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(TeamActivity.this, AddPlayer.class);
            addPlayerLauncher.launch(intent);
        });
    }

    // Receive updated team name from TeamActivity1
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_TEAM_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            String updatedName = data.getStringExtra("teamName");
            if (updatedName != null && !updatedName.isEmpty()) {
                teamNameInput.setText(updatedName);
            }
        }
    }
}
