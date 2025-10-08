package com.example.hockey;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private LinearLayout teamContainer;
    private TextView noTeamsText;
    private Button addTeamBtn;
    private ActivityResultLauncher<Intent> teamActivityLauncher;
    private ActivityResultLauncher<Intent> teamActivity1Launcher;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        teamContainer = findViewById(R.id.teamContainer);
        noTeamsText = findViewById(R.id.noTeamsText);
        addTeamBtn = findViewById(R.id.addTeamBtn);
        bottomNavigation = findViewById(R.id.bottom_navigation); // <--- add this line

        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                return true;
            } else if (itemId == R.id.nav_teams) {
                Toast.makeText(this, "Teams clicked", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.nav_score) {
                Toast.makeText(this, "Score clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
        // Add new team
        teamActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String teamName = result.getData().getStringExtra("teamName");
                        if (teamName != null && !teamName.isEmpty()) {
                            addTeamCard(teamName);
                        }
                    }
                }
        );

        // Edit existing team
        teamActivity1Launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String updatedName = result.getData().getStringExtra("teamName");
                        int position = result.getData().getIntExtra("position", -1);

                        if (updatedName != null && position != -1) {
                            LinearLayout teamCard = (LinearLayout) teamContainer.getChildAt(position);
                            TextView teamNameLabel = teamCard.findViewById(R.id.teamNameLabel);
                            teamNameLabel.setText(updatedName);
                        }
                    }
                }
        );

        addTeamBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TeamActivity.class);
            teamActivityLauncher.launch(intent);
        });
    }

    private void addTeamCard(String teamName) {
        noTeamsText.setVisibility(TextView.GONE);

        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout teamCard = (LinearLayout) inflater.inflate(R.layout.item_team_card, teamContainer, false);

        TextView teamNameLabel = teamCard.findViewById(R.id.teamNameLabel);
        teamNameLabel.setText(teamName);

        ImageView editIcon = teamCard.findViewById(R.id.editIcon);
        ImageView deleteIcon = teamCard.findViewById(R.id.deleteIcon);

        int position = teamContainer.getChildCount(); // position of this card

        editIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TeamActivity1.class);
            intent.putExtra("teamName", teamNameLabel.getText().toString());
            intent.putExtra("position", position);
            teamActivity1Launcher.launch(intent);
        });

        deleteIcon.setOnClickListener(v -> {
            teamContainer.removeView(teamCard);
            if (teamContainer.getChildCount() == 0) {
                noTeamsText.setVisibility(TextView.VISIBLE);
            }
        });

        teamContainer.addView(teamCard);
    }
}
