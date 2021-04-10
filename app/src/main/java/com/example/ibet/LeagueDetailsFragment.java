package com.example.ibet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ibet.model.Group.Group;
import com.example.ibet.model.Group.GroupAdapter;
import com.example.ibet.model.Group.GroupViewModel;
import com.example.ibet.model.Model;
import com.example.ibet.model.Team.Team;
import com.example.ibet.model.Team.TeamAdapter;
import com.example.ibet.model.Team.TeamViewModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LeagueDetailsFragment extends Fragment {

    View view;

    public RecyclerView teamsList_rv;
    TeamAdapter teamAdapter;
    private TeamViewModel teamViewModel;
    List<Team> teamList = new LinkedList<Team>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_league_details, container, false);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        teamsList_rv = view.findViewById(R.id.league_details_rv);
        teamsList_rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        teamsList_rv.setLayoutManager(layoutManager);
        teamAdapter = new TeamAdapter();

        teamViewModel = ViewModelProviders.of(getActivity()).get(TeamViewModel.class);

        Model.instance.getTeamData(new Model.TeamDataListener() {
            @Override
            public void onComplete(ArrayList<Team> teamData) {
                teamList = teamData;
                teamAdapter.setTeamsData(teamList);
                teamsList_rv.setAdapter(teamAdapter);
                for (Team team : teamList) {
                    teamViewModel.insert(team);
                }
            }
        });

        teamAdapter.setOnItemClickListener(new TeamAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Team team, View view) {
                String teamName = team.getTeamName();
                String teamWins = team.getWins();
                String teamLoss = team.getLosses();
                String teamRemaining = team.getGamesRemaining();
                LeagueDetailsFragmentDirections
                        .ActionLeagueDetailsFragmentToTeamDetailsFragment action = LeagueDetailsFragmentDirections
                        .actionLeagueDetailsFragmentToTeamDetailsFragment(teamName,teamWins,teamLoss,teamRemaining);
                Navigation.findNavController(view).navigate(action);
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