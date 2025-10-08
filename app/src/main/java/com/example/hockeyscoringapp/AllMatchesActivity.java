package com.example.hockeyscoringapp;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.List;



public class AllMatchesActivity extends AppCompatActivity implements MatchAdapter.OnMatchClickListener {

    private RecyclerView allMatchesRecyclerView;

    private MatchAdapter matchAdapter;
    private ImageButton backButton;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_all_matches);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        allMatchesRecyclerView = findViewById(R.id.allMatchesRecyclerView);
        backButton = findViewById(R.id.backButton);
        allMatchesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        backButton.setOnClickListener(view -> finish());
// Get a list of more matches (5+ as requested)

        List<Match> allMatches = getAllSampleMatches();

// Pass 'this' as the listener since this activity implements OnMatchClickListener

        matchAdapter = new MatchAdapter(allMatches, this);

        allMatchesRecyclerView.setAdapter(matchAdapter);



    }

    @Override



    public void onMatchClick(Match match) {



        Toast.makeText(



                this,



                "You can view the complete score message for: " + match.getTeamA() + " vs " + match.getTeamB(),



                Toast.LENGTH_SHORT



        ).show();



    }







// A method to get a larger list of sample matches



    private List<Match> getAllSampleMatches() {


        List<Match> list = new ArrayList<>();


// 5 matches as requested, plus one for scrolling


        list.add(new Match("16-09-2025", "11PM", "Team A", "Team B"));


        list.add(new Match("18-09-2025", "12PM", "Team C", "Team D"));


        list.add(new Match("20-09-2025", "10AM", "Team E", "Team F"));


        list.add(new Match("22-09-2025", "08PM", "Team G", "Team H"));


        list.add(new Match("24-09-2025", "04PM", "Team I", "Team J"));


        list.add(new Match("26-09-2025", "06PM", "Team K", "Team L"));


        return list;


    }
    }