package com.example.ibet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ibet.model.Bets.Bet;
import com.example.ibet.model.Match.Match;
import com.example.ibet.model.Match.MatchAdapter;
import com.example.ibet.model.Model;
import com.example.ibet.model.User.User;

import java.util.ArrayList;
import java.util.List;


public class UserBetsPopUpFragment extends Fragment {

    View view;

    public RecyclerView matchesList_rv;
    MatchAdapter matchAdapter;

    List<Match> MatchList = new ArrayList<>();

    String groupId;
    String userId;
    String userName;

    ArrayList<Bet> bets = new ArrayList<>();

    ProgressBar pb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_bets_pop_up, container, false);

        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        matchesList_rv = view.findViewById(R.id.user_popup_rv);
        matchesList_rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        matchesList_rv.setLayoutManager(layoutManager);

        matchAdapter = new MatchAdapter();

        groupId = UserBetsPopUpFragmentArgs.fromBundle(getArguments()).getGroupId();
        userId = UserBetsPopUpFragmentArgs.fromBundle(getArguments()).getUserId();
        userName = UserBetsPopUpFragmentArgs.fromBundle(getArguments()).getUserName();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(userName + " Bets");

        pb=view.findViewById(R.id.upcoming_pb2);
        pb.setVisibility(View.VISIBLE);

        Model.instance.getCurrentUserDetails(new Model.UserDetailsListener() {
            @Override
            public void onComplete(User user) {
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

                ArrayList<Match> updatedFinishedMatches= new ArrayList<>();

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

                MatchList.addAll(updatedFinishedMatches);
                int pos = updatedFinishedMatches.size()-1;
                matchAdapter.setMatchesData(MatchList);
                matchesList_rv.setAdapter(matchAdapter);
                matchesList_rv.scrollToPosition(pos);
                pb.setVisibility(View.INVISIBLE);

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