package com.example.ibet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    List<Match> matchList = new ArrayList<Match>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_upcoming_matches, container, false);

        matchesList_rv = view.findViewById(R.id.upcoming_matches_rv);
        matchesList_rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        matchesList_rv.setLayoutManager(layoutManager);
        matchAdapter = new MatchAdapter();

        matchViewModel = ViewModelProviders.of(getActivity()).get(MatchViewModel.class);

        Model.instance.getUpComingMatches(new Model.MatchListener() {
            @Override
            public void onComplete(ArrayList<Match> upComingMatches) {
                matchList = upComingMatches;
                matchAdapter.setMatchesData(matchList);
                matchesList_rv.setAdapter(matchAdapter);
                for (Match match : matchList) {
                    matchViewModel.insert(match);
                }
            }
        });




        return view;
    }
}