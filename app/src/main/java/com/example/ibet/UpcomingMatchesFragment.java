package com.example.ibet;

import android.os.Bundle;

import androidx.annotation.NonNull;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_upcoming_matches, container, false);
        setHasOptionsMenu(true);

        matchesList_rv = view.findViewById(R.id.upcoming_matches_rv);
        matchesList_rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        matchesList_rv.setLayoutManager(layoutManager);

        matchAdapter = new MatchAdapter();

        winningTeam = view.findViewById(R.id.upcoming_winner_input);
        totalScore = view.findViewById(R.id.upcoming_score_input);
        confirmBtn = view.findViewById(R.id.upcoming_confirm_btn);

        //matchViewModel = ViewModelProviders.of(getActivity()).get(MatchViewModel.class);

        groupId = UpcomingMatchesFragmentArgs.fromBundle(getArguments()).getGroupId();

        pb=view.findViewById(R.id.upcoming_pb);
        pb.setVisibility(View.VISIBLE);

        Model.instance.getUpComingMatches(new Model.MatchListener() {
            @Override
            public void onComplete(ArrayList<Match> finishedMatches,
                                   ArrayList<Match> thisWeekMatches,
                                   ArrayList<Match> notYetMatches) {
                MatchList.addAll(finishedMatches);
                int pos = finishedMatches.size();
                MatchList.addAll(thisWeekMatches);
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

//        Model.instance.getGroupBets(groupId, new Model.BetListener() {
//            @Override
//            public void onComplete(ArrayList<Bet> betsLists) {
//                String betGameId;
//                String winner;
//                String totalScore;
//                for(int i=0;i<betsLists.size();i++){
//                    betGameId = betsLists.get(i).getGameId();
//                    winner = betsLists.get(i).getWinner();
//                    totalScore = betsLists.get(i).getTotalScore();
//                    for(int j=0;j<thisWeekMatches.size();j++){
//                        if(betGameId.equals(thisWeekMatches.get(j).getId())){
//                            winningTeam.setText(winner);
//                        }
//                        else{
//                            winningTeam.setText("0");
//                        }
//                    }
//                }
//            }
//        });

        matchAdapter.setOnItemClickListener(new MatchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Match match, View view) {
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