package com.example.ibet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ibet.model.Model;
import com.example.ibet.model.Team.Team;

import java.util.ArrayList;


public class MyProfileFragment extends Fragment {
    View view;

    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        button=view.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.instance.getAlgoResult(new Model.TeamDataListener() {
                    @Override
                    public void onComplete(ArrayList<Team> teamData) {

                    }
                });
            }
        });

        return view;
    }
}