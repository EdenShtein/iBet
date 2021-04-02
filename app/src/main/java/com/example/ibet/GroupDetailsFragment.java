package com.example.ibet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class GroupDetailsFragment extends Fragment {

    View view;
    TextView league;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_group_details, container, false);
        setHasOptionsMenu(true);

        league = view.findViewById(R.id.group_details_sub);

        league.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_groupDetailsFragment_to_leagueDetailsFragment);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.group_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.group_back:
                if(view != null) {
                    Navigation.findNavController(view).navigate(R.id.action_groupDetailsFragment_to_mainFeedFragment);
                }
                break;
            case R.id.menu_invite:
                if(view != null) {

                }
                break;
            default:

        }
        return super.onOptionsItemSelected(item);

    }
}