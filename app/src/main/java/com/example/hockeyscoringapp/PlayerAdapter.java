package com.example.hockeyscoringapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private final List<Player> originalPlayerList;
    private List<Player> filteredPlayerList;

    public PlayerAdapter(List<Player> playerList) {
        this.originalPlayerList = playerList;
        this.filteredPlayerList = new ArrayList<>(playerList);
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_player, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = filteredPlayerList.get(position); // Use filtered list

        // Set name on the button
        holder.playerNameButton.setText(player.getName());

        // Show a toast when this button is clicked (Player Card Clickable)
        holder.playerNameButton.setOnClickListener(v ->
                Toast.makeText(
                        v.getContext(),
                        player.getName() + " is clicked",
                        Toast.LENGTH_SHORT
                ).show()
        );
    }

    @Override
    public int getItemCount() {
        return filteredPlayerList.size(); // Count from filtered list
    }

    // =========================================================
    // NEW: Filtering Method
    // =========================================================
    public void filter(String query) {
        // Make sure we always compare in a case-insensitive way
        String prefix = query.trim().toLowerCase(Locale.ROOT);

        filteredPlayerList.clear();

        if (prefix.isEmpty()) {
            filteredPlayerList.addAll(originalPlayerList);
        } else {
            for (Player p : originalPlayerList) {
                if (p.getName().toLowerCase(Locale.ROOT).startsWith(prefix)) {
                    filteredPlayerList.add(p);
                }
            }
        }
        notifyDataSetChanged();
    }

    // =========================================================

    static class PlayerViewHolder extends RecyclerView.ViewHolder {
        ImageView playerPhotoImageView;
        Button playerNameButton;

        PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            // Assuming IDs inside card_player are correct
            playerPhotoImageView = itemView.findViewById(R.id.playerPhotoImageView);
            playerNameButton = itemView.findViewById(R.id.playerNameButton);
        }
    }
}
class Player {
    private final String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}