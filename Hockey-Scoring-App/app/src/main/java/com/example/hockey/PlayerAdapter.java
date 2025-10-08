package com.example.hockey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private ArrayList<Player> playerList;
    private OnPlayerDeleteListener deleteListener;

    // Listener interface for delete events
    public interface OnPlayerDeleteListener {
        void onPlayerDeleted();
    }

    public void setOnPlayerDeleteListener(OnPlayerDeleteListener listener) {
        this.deleteListener = listener;
    }

    public PlayerAdapter(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.playerdetails, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = playerList.get(position);
        holder.nameText.setText("Name: " + player.getName());
        holder.jerseyText.setText("Jersey No: " + player.getJersey());
        holder.positionText.setText("Position: " + player.getPosition());

        // 🔹 Click listener for the whole player card
        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(v.getContext(),
                    "Clicked player: " + player.getName(),
                    Toast.LENGTH_SHORT).show();
        });

        holder.deleteBtn.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                playerList.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, playerList.size());

                if (deleteListener != null) {
                    deleteListener.onPlayerDeleted();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    static class PlayerViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, jerseyText, positionText;
        ImageButton deleteBtn;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameText);
            jerseyText = itemView.findViewById(R.id.jerseyText);
            positionText = itemView.findViewById(R.id.positionText);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}
