package com.example.ibet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class TeamDetailsFragment extends Fragment {

    View view;
    TextView team_name;
    TextView team_win;
    TextView team_loss;
    TextView team_remaining;
    String teamLogoUrl;
    ImageView teamLogo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_team_details, container, false);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        team_name = view.findViewById(R.id.team_details_title);
        team_win = view.findViewById(R.id.team_details_win_input);
        team_loss = view.findViewById(R.id.team_details_loss_input);
        team_remaining = view.findViewById(R.id.team_details_remaining_input);
        teamLogo = view.findViewById(R.id.team_details_img);

        team_name.setText(TeamDetailsFragmentArgs.fromBundle(getArguments()).getTeamName());
        team_win.setText(TeamDetailsFragmentArgs.fromBundle(getArguments()).getTeamWins());
        team_loss.setText(TeamDetailsFragmentArgs.fromBundle(getArguments()).getTeamLoss());
        team_remaining.setText(TeamDetailsFragmentArgs.fromBundle(getArguments()).getTeamRemaining());
        teamLogoUrl = TeamDetailsFragmentArgs.fromBundle(getArguments()).getTeamLogo();

        if (teamLogoUrl != null) {
            Picasso.get().load(teamLogoUrl).placeholder(R.drawable.brplayer).into(teamLogo);
        }

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