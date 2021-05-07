package com.example.ibet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ibet.model.Bets.Bet;
import com.example.ibet.model.Match.Match;
import com.example.ibet.model.Match.MatchAdapter;
import com.example.ibet.model.Match.MatchViewModel;
import com.example.ibet.model.Model;
import com.example.ibet.model.Team.Team;
import com.example.ibet.model.Team.TeamAdapter;
import com.example.ibet.model.Team.TeamViewModel;
import com.example.ibet.model.User.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UpcomingMatchesFragment extends Fragment {

    View view;

    public RecyclerView matchesList_rv;
    MatchAdapter matchAdapter;
    private MatchViewModel matchViewModel;

    List<Match> MatchList = new ArrayList<>();

    Button confirmBtn;
    EditText winningTeam;
    EditText totalScore;

    String groupId;
    String winning;
    String score;

    ProgressBar pb;

    ArrayList<Bet> bets;
    String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_upcoming_matches, container, false);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Upcoming Matches");

        matchesList_rv = view.findViewById(R.id.upcoming_matches_rv);
        matchesList_rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        matchesList_rv.setLayoutManager(layoutManager);

        matchAdapter = new MatchAdapter();

        bets = new ArrayList<>();

        //matchViewModel = ViewModelProviders.of(getActivity()).get(MatchViewModel.class);

        groupId = UpcomingMatchesFragmentArgs.fromBundle(getArguments()).getGroupId();

        pb=view.findViewById(R.id.upcoming_pb);
        pb.setVisibility(View.VISIBLE);



        Model.instance.getCurrentUserDetails(new Model.UserDetailsListener() {
            @Override
            public void onComplete(User user) {
                userId = user.getId();
                Model.instance.getGroupBets(userId,groupId, new Model.BetListener() {
                    @Override
                    public void onComplete(ArrayList<Bet> betsLists) {
                        bets = betsLists;
                    }
                });
            }
        });




        Model.instance.getUpComingMatches(new Model.MatchListener() {
            @Override
            public void onComplete(ArrayList<Match> finishedMatches,
                                   ArrayList<Match> thisWeekMatches,
                                   ArrayList<Match> notYetMatches) {
                MatchList.addAll(finishedMatches);
                int pos = finishedMatches.size();


                ArrayList<Match> updatedThisWeekMatches= new ArrayList<>();
                ArrayList<Match> updatedFinishedMatches= new ArrayList<>();

                for(int k=0;k<thisWeekMatches.size();k++){
                    Match match = thisWeekMatches.get(k);
                    match.setUserBet(new Bet("0","0",match.getId()));
                    updatedThisWeekMatches.add(match);
                }
                for(int k=0;k<finishedMatches.size();k++){
                    Match match = finishedMatches.get(k);
                    match.setUserBet(new Bet("0","0",match.getId()));
                    updatedFinishedMatches.add(match);
                }
                for (int i = 0; i < thisWeekMatches.size(); i++) {
                    Match match = thisWeekMatches.get(i);
                    for (int j = 0; j < bets.size(); j++) {
                        if (bets.get(j).getGameId().equals(match.getId())) {
                            match.setUserBet(bets.get(j));
                            break;
                        } else {
                            match.setUserBet(new Bet("0", "0", match.getId()));
                        }
                    }
                    updatedThisWeekMatches.add(match);
                }
                for (int i = 0; i < finishedMatches.size(); i++) {
                    Match match = finishedMatches.get(i);
                    for (int j = 0; j < bets.size(); j++) {
                        if (bets.get(j).getGameId().equals(match.getId())) {
                            match.setUserBet(bets.get(j));
                            break;
                        } else {
                            match.setUserBet(new Bet("0", "0", match.getId()));
                        }
                    }
                    updatedFinishedMatches.add(match);
                }




                MatchList.addAll(updatedThisWeekMatches);
                MatchList.addAll(notYetMatches);
                matchAdapter.setMatchesData(MatchList);
                matchesList_rv.setAdapter(matchAdapter);
                matchesList_rv.scrollToPosition(pos);
                /*for (Match match : MatchList) {
                    matchViewModel.insert(match);
                }*/

                pb.setVisibility(View.INVISIBLE);


            }
        });



        matchAdapter.setOnItemClickListener(new MatchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Match match, View view) {

                winningTeam = view.findViewById(R.id.upcoming_winner_input);
                totalScore = view.findViewById(R.id.upcoming_score_input);
                confirmBtn = view.findViewById(R.id.upcoming_confirm_btn);

                if (match.isBetted()){
                    confirmBtn.setVisibility(View.INVISIBLE);
                    confirmBtn.setEnabled(false);
                    winningTeam.setEnabled(false);
                    totalScore.setEnabled(false);
                }
                confirmBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        winning = winningTeam.getText().toString();
                        score = totalScore.getText().toString();
                        if (winning.isEmpty() || score.isEmpty() || winning.charAt(0)>= '3' || winning.charAt(0) <= '0'){
                            Toast.makeText(getActivity(), "Please Enter Winner 1 or 2 and score above 0", Toast.LENGTH_SHORT).show();
                        }else{
                            Log.d("TAG",winning);
                            Log.d("TAG",score);

                            Model.instance.placeBet(groupId, winning, score, match.getId(), new Model.SuccessListener() {
                                @Override
                                public void onComplete(boolean result) {
                                    if (result){
                                        Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                                        confirmBtn.setVisibility(View.INVISIBLE);
                                        confirmBtn.setEnabled(false);
                                        winningTeam.setEnabled(false);
                                        totalScore.setEnabled(false);
                                        match.setBetted(true);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });



        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.back_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back_btn:
                if(view != null) {
                    Navigation.findNavController(view).popBackStack();
                }
                break;
            default:

        }

        return super.onOptionsItemSelected(item);
    }
}