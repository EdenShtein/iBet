package com.example.ibet.model.Match;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ibet.R;
import java.util.LinkedList;
import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchHolder>  {

    public static List<Match> matchesData = new LinkedList<Match>();
    private static OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public Match getMatches(int position)
    {
        return matchesData.get(position);
    }

    public void setMatchesData(List<Match> matches) {
        this.matchesData = matches;
        notifyDataSetChanged();
    }

    // Create MatchHolder for the adapter.
    @NonNull
    @Override
    public MatchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_matches_listrow, parent, false);
        MatchHolder holder = new MatchHolder(view);
        return holder;
    }

    // Bind data to the adapter.
    @Override
    public void onBindViewHolder(@NonNull MatchHolder holder, int position) {
        Match currentMatch = matchesData.get(position);
        holder.bindData(currentMatch,position);
        holder.itemView.setTag(currentMatch);
    }

    @Override
    public int getItemCount() {
        return matchesData.size();
    }

    public interface OnItemClickListener{
        void onItemClick(Match match, View view);
    }

    //---------------MatchHolder----------------//

    public static class MatchHolder extends RecyclerView.ViewHolder{
        TextView matchHome;
        TextView matchAway;
        TextView matchDate;
        ImageView matchImage;
        int position;

        public MatchHolder(@NonNull View itemView) {
            super(itemView);
            matchImage = itemView.findViewById(R.id.upcoming_img);
            matchHome = itemView.findViewById(R.id.upcoming_home_title);
            matchAway = itemView.findViewById(R.id.upcoming_away_title);
            matchDate = itemView.findViewById(R.id.upcoming_match_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(matchesData.get(position), v);
                    }
                }
            });
        }

        public void bindData(Match match, int position){
            matchHome.setText(match.getHome());
            matchAway.setText(match.getAway());
            matchDate.setText(match.getDate());
            matchImage.setImageResource(R.drawable.brplayer);
           /* if (group.getGroupLogo() != null) {
                Picasso.get().load(group.getGroupLogo()).placeholder(R.drawable.brplayer).into(groupImage);
            }*/

            this.position = position;
        }
    }

}
