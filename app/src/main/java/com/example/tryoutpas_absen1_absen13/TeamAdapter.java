package com.example.tryoutpas_absen1_absen13;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    private List<Team> teamList;


    public TeamAdapter(List<Team> teamList) {
        this.teamList = teamList;
    }

    @Override
    public TeamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team, parent, false);
        return new TeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TeamViewHolder holder, int position) {
        Team team = teamList.get(position);
        holder.txtTeamName.setText(team.getStrTeam());
        holder.txtStadium.setText(team.getStrStadium());

        Glide.with(holder.itemView.getContext())
                .load(team.getStrBadge())
                .into(holder.imgBadge);

    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }

    public class TeamViewHolder extends RecyclerView.ViewHolder {
        TextView txtTeamName, txtStadium;
        ImageView imgBadge;



        public TeamViewHolder(View itemView) {
            super(itemView);
            txtTeamName = itemView.findViewById(R.id.txtTeamName);
            txtStadium = itemView.findViewById(R.id.txtStadium);
            imgBadge = itemView.findViewById(R.id.imgBadge);
        }
    }
}
