package com.example.ibet.model.Team;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ibet.R;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamHolder>  {

    public static List<Team> teamData = new LinkedList<Team>();
    private static OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public Team getTeams(int position)
    {
        return teamData.get(position);
    }

    public void setTeamsData(List<Team> teams) {
        this.teamData = teams;
        notifyDataSetChanged();
    }

    // Create TeamHolder for the adapter.
    @NonNull
    @Override
    public TeamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_list_row, parent, false);
        TeamHolder holder = new TeamHolder(view);
        return holder;
    }

    // Bind data to the adapter.
    @Override
    public void onBindViewHolder(@NonNull TeamHolder holder, int position) {
        Team currentTeam = teamData.get(position);
        holder.bindData(currentTeam,position);
        holder.itemView.setTag(currentTeam);
    }

    @Override
    public int getItemCount() {
        return teamData.size();
    }

    public interface OnItemClickListener{
        void onItemClick(Team Team, View view);
    }

    //---------------TeamHolder----------------//

    public static class TeamHolder extends RecyclerView.ViewHolder{

        TextView teamText;
        TextView teamWins;
        TextView teamLosses;
        TextView teamRemaining;
        ImageView teamImage;
        int position;

        public TeamHolder(@NonNull View itemView) {
            super(itemView);
            teamImage = itemView.findViewById(R.id.team_list_row_img);
            teamText = itemView.findViewById(R.id.team_list_row_title);
            teamWins = itemView.findViewById(R.id.team_list_row_w_input);
            teamLosses = itemView.findViewById(R.id.team_list_row_l_input);
            teamRemaining = itemView.findViewById(R.id.team_list_row_r_input);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(teamData.get(position), v);
                    }
                }
            });
        }

        public void bindData(Team team, int position){

            teamText.setText(team.getTeamName());
            teamWins.setText(team.getWins());
            teamLosses.setText(team.getLosses());
            teamRemaining.setText(team.getGamesRemaining());
            if (team.getUrl() != null) {
                Picasso.get().load(team.getUrl()).placeholder(R.drawable.brplayer).into(teamImage);
            }

            this.position = position;
        }
    }

}
