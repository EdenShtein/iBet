package com.example.ibet.model.Match;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ibet.R;
import com.squareup.picasso.Picasso;

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

    public class MatchHolder extends RecyclerView.ViewHolder{
        TextView matchHome;
        TextView matchAway;
        TextView matchDate;
        TextView HomeScore;
        TextView AwayScore;
        ImageView homeImage;
        ImageView awayImage;
        Spinner winnerInput;
        EditText scoreInput;
        TextView winnerTitle;
        TextView scoreTitle;
        Button confirmBtn;
        int position;
        Spinner teamDropDown;

        public MatchHolder(@NonNull View itemView) {

            super(itemView);
            homeImage = itemView.findViewById(R.id.upcoming_home_img);
            awayImage = itemView.findViewById(R.id.upcoming_away_img);
            matchHome = itemView.findViewById(R.id.upcoming_home_title);
            matchAway = itemView.findViewById(R.id.upcoming_away_title);
            matchDate = itemView.findViewById(R.id.upcoming_match_date);
            HomeScore = itemView.findViewById(R.id.upcoming_home_score);
            AwayScore = itemView.findViewById(R.id.upcoming_away_score);
            winnerInput = itemView.findViewById(R.id.upcoming_winner_input);
            scoreInput = itemView.findViewById(R.id.upcoming_score_input);
            confirmBtn = itemView.findViewById(R.id.upcoming_confirm_btn);
            winnerTitle = itemView.findViewById(R.id.upcoming_winner_title);
            scoreTitle = itemView.findViewById(R.id.upcoming_score_title);

            winnerInput = itemView.findViewById(R.id.upcoming_winner_input);
            

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

            String[] teams = new String[]{"Select Team",match.getHome(),match.getAway()};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(itemView.getContext(), android.R.layout.simple_spinner_dropdown_item, teams);
            winnerInput.setAdapter(adapter);

            if (match.getStatus().equals("Finished") || match.getStatus().equals("NotYet")){
                winnerInput.setEnabled(false);
                winnerInput.setVisibility(View.VISIBLE);
                if (match.getUserBet() == null){
                    winnerInput.setSelection(0);
                    scoreInput.setText("0");
                }else{
                    winnerInput.setSelection(Integer.parseInt(match.getUserBet().getWinner()));
                    scoreInput.setText(match.getUserBet().getTotalScore());
                }
                scoreInput.setEnabled(false);
                scoreInput.setVisibility(View.VISIBLE);
                confirmBtn.setEnabled(false);
                confirmBtn.setVisibility(View.INVISIBLE);
                winnerTitle.setVisibility(View.INVISIBLE);
                scoreTitle.setVisibility(View.INVISIBLE);
            }
            else{
                winnerInput.setEnabled(true);
                winnerInput.setVisibility(View.VISIBLE);
                if (match.getUserBet() == null){
                    winnerInput.setSelection(0);
                    scoreInput.setText("0");
                }else{
                    winnerInput.setSelection(Integer.parseInt(match.getUserBet().getWinner()));
                    scoreInput.setText(match.getUserBet().getTotalScore());
                }
                scoreInput.setEnabled(true);
                scoreInput.setVisibility(View.VISIBLE);
                confirmBtn.setEnabled(true);
                confirmBtn.setVisibility(View.VISIBLE);
                winnerTitle.setVisibility(View.VISIBLE);
                scoreTitle.setVisibility(View.VISIBLE);
            }
            matchHome.setText(match.getHome());
            matchAway.setText(match.getAway());
            matchDate.setText(match.getDate());
            HomeScore.setText(String.valueOf(match.gethScore()));
            AwayScore.setText(String.valueOf(match.getvScore()));

            String homeUrl = match.getHomeImageUrl();
            String awayUrl = match.getAwayImageUrl();

            if (homeUrl != null) {
                Picasso.get().load(homeUrl).placeholder(R.drawable.brplayer).into(homeImage);
            }
            if (awayUrl != null) {
                Picasso.get().load(awayUrl).placeholder(R.drawable.brplayer).into(awayImage);
            }

            this.position = position;
        }
    }

}
