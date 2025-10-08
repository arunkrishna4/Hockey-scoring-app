package com.example.hockey;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;

public class AddPlayer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_player);

        // Back button: navigate back without saving
        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> {
            setResult(Activity.RESULT_CANCELED);
            finish();
        });

        // Inputs
        EditText playerNameInput = findViewById(R.id.playerNameInput);
        EditText jerseyNoInput = findViewById(R.id.jerseyNoInput);
        Spinner positionSpinner = findViewById(R.id.positionSpinner);

        // Spinner options
        String[] positions = {"Select", "Goalkeeper", "Defender", "Midfielder", "Forward"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, positions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        positionSpinner.setAdapter(adapter);

        // Save Player button → send data back to TeamActivity1
        Button savePlayerBtn = findViewById(R.id.savePlayerBtn);
        savePlayerBtn.setOnClickListener(v -> {
            String name = playerNameInput.getText().toString().trim();
            String jersey = jerseyNoInput.getText().toString().trim();
            String position = positionSpinner.getSelectedItem().toString();

            // Validation
            if (name.isEmpty()) {
                playerNameInput.setError("Enter player name");
                playerNameInput.requestFocus();
                return;
            }
            if (jersey.isEmpty()) {
                jerseyNoInput.setError("Enter jersey number");
                jerseyNoInput.requestFocus();
                return;
            }
            if (!jersey.matches("\\d+")) {
                jerseyNoInput.setError("Enter a valid number");
                jerseyNoInput.requestFocus();
                return;
            }
            if (position.equals("Select")) {
                Toast.makeText(AddPlayer.this, "Please select a position", Toast.LENGTH_SHORT).show();
                positionSpinner.requestFocus();
                return;
            }

            // Send result back to TeamActivity1
            Intent resultIntent = new Intent();
            resultIntent.putExtra("playerName", name);
            resultIntent.putExtra("jerseyNo", jersey);
            resultIntent.putExtra("position", position);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });
    }
}
