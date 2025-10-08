package com.example.hockeyscoringapp;

import android.graphics.Rect;
import android.view.View;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView; // Import SearchView
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class AllPlayersActivity extends AppCompatActivity {

    private RecyclerView allPlayersRecyclerView;
    private RecyclerView mostGoalsRecyclerView;
    private PlayerAdapter allPlayersAdapter;
    private PlayerAdapter mostGoalsAdapter;
    private ImageButton backButton;
    private SearchView searchView; // Declare SearchView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_players);

        // FIX: Hide the default Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Find views
        allPlayersRecyclerView = findViewById(R.id.allPlayersRecyclerView);
        mostGoalsRecyclerView = findViewById(R.id.mostGoalsRecyclerView);
        backButton = findViewById(R.id.backButton);

        // Find the new SearchView ID
        searchView = findViewById(R.id.searchView);

        // All Players grid with spacing
        allPlayersRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        allPlayersAdapter = new PlayerAdapter(getSamplePlayers());
        allPlayersRecyclerView.setAdapter(allPlayersAdapter);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.recycler_item_spacing);
        allPlayersRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(spacingInPixels));

        // Most Goals grid with spacing (This list should not be filtered by search, so we use a separate adapter)
        mostGoalsRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mostGoalsAdapter = new PlayerAdapter(getSampleMostGoalsPlayers());
        mostGoalsRecyclerView.setAdapter(mostGoalsAdapter);
        mostGoalsRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(spacingInPixels));

        // Handle button clicks
        backButton.setOnClickListener(view -> finish());

        // =========================================================
        // NEW: Search View Listener Implementation
        // =========================================================
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                allPlayersAdapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                allPlayersAdapter.filter(newText);
                return false;
            }
        });

        // =========================================================
    }

    private List<Player> getSamplePlayers() {
        List<Player> players = new ArrayList<>();
        // Using player names as required by the search functionality
        players.add(new Player("Player 1"));
        players.add(new Player("Player 2"));
        players.add(new Player("Player 3"));
        players.add(new Player("Player 4"));
        players.add(new Player("Player 5"));
        players.add(new Player("Player 6"));
        return players;
    }

    private List<Player> getSampleMostGoalsPlayers() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Player 1"));
        players.add(new Player("Player 2"));
        players.add(new Player("Player 3"));
        return players;
    }

    private static class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
        private final int verticalSpaceHeight;

        VerticalSpaceItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = verticalSpaceHeight;
        }
    }
}