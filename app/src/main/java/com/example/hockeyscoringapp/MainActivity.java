package com.example.hockeyscoringapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.Button;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.LinearLayout; // Import for playersSection

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

// Implement the OnMatchClickListener interface
public class MainActivity extends AppCompatActivity implements MatchAdapter.OnMatchClickListener {

    private RecyclerView matchesRecyclerView;
    private RecyclerView playersRecyclerView;
    private MatchAdapter matchAdapter;
    private PlayerAdapter playerAdapter;
    private ImageButton exitButton;
    private ImageButton viewAllPlayersButton;
    private Button addMatchButton;
    private Button viewAllButton; // Corresponds to the "View All" button in XML
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FIX: Remove the default Action Bar to solve the double header
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Initialize UI components
        matchesRecyclerView = findViewById(R.id.matchesRecyclerView);
        playersRecyclerView = findViewById(R.id.playersRecyclerView);
        exitButton = findViewById(R.id.exitButton);
        viewAllPlayersButton = findViewById(R.id.viewAllPlayersButton);
        addMatchButton = findViewById(R.id.addMatchButton);
        viewAllButton = findViewById(R.id.viewAllButton);

        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);

        // Set up Matches RecyclerView
        matchesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Match> matches = getSampleMatches();
        matchAdapter = new MatchAdapter(matches, this);
        matchesRecyclerView.setAdapter(matchAdapter);

        // Set up Players RecyclerView (horizontal)
        playersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<Player> players = getSamplePlayers();
        playerAdapter = new PlayerAdapter(players);
        playersRecyclerView.setAdapter(playerAdapter);

        // Handle button clicks
        exitButton.setOnClickListener(view -> finish());

        // Handle View All Matches button click to navigate to the new page
        viewAllButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AllMatchesActivity.class);
            startActivity(intent);
        });

        viewAllPlayersButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AllPlayersActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Navigating to All Players page", Toast.LENGTH_SHORT).show();
        });

        addMatchButton.setOnClickListener(view -> {
            Toast.makeText(this, "Add Match button clicked!", Toast.LENGTH_SHORT).show();
        });

        // Set up Bottom Navigation Bar listener
        bottomNavigationView.setOnItemSelectedListener(item -> {
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
    }

    // Implementing the click interface to show the toast message for match cards (on the main page)
    @Override
    public void onMatchClick(Match match) {
        Toast.makeText(
                this,
                "You can view the complete score message for: " + match.getTeamA() + " vs " + match.getTeamB(),
                Toast.LENGTH_SHORT
        ).show();
    }

    private List<Match> getSampleMatches() {
        List<Match> list = new ArrayList<>();
        list.add(new Match("16-09-2025", "11PM", "Team A", "Team B"));
        list.add(new Match("18-09-2025", "12PM", "Team C", "Team D"));
        list.add(new Match("20-09-2025", "10AM", "Team E", "Team F"));
        // Only 3 matches for the main screen preview
        return list;
    }

    private List<Player> getSamplePlayers() {
        List<Player> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            list.add(new Player("Player " + i));
        }
        return list;
    }
}