package com.example.ibet;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ibet.model.Model;


public class DashboardFragment extends Fragment {

    View view;

    CardView signout;
    CardView teamsResult;
    CardView back;
    CardView rules;
    CardView profile;
    CardView league;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Button pastBtn;
    Button presentBtn;

    AlertDialog.Builder alertBuilder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        //setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        signout =view.findViewById(R.id.dash_signout);
        teamsResult =view.findViewById(R.id.dash_team_results);
        back = view.findViewById(R.id.dash_nav_back);
        rules = view.findViewById(R.id.dash_nav_rules);
        profile = view.findViewById(R.id.dash_myprofile);
        league = view.findViewById(R.id.dash_league);
        alertBuilder = new AlertDialog.Builder(getActivity());

        pastBtn = view.findViewById(R.id.dash_past_btn);
        presentBtn = view.findViewById(R.id.dash_present_btn);

        pastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.instance.backInTime(new Model.SuccessListener() {
                    @Override
                    public void onComplete(boolean result) {
                        if (result){
                            Toast.makeText(getActivity(), "back in time Marty", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        presentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.instance.backToFuture(new Model.SuccessListener() {
                    @Override
                    public void onComplete(boolean result) {
                        Toast.makeText(getActivity(), "back to the future Marty", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        pref = getActivity().getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertBuilder.setMessage("Are You Sure You Want To Sign Out?")
                        .setCancelable(false)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pref.edit().remove("token").commit();
                                Navigation.findNavController(view).navigate(R.id.action_dashboard_to_login);
                            }
                        });
                AlertDialog alert = alertBuilder.create();
                alert.setTitle("Sign Out");
                alert.show();
            }

        });

        teamsResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_dashboard_to_teamsResult);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_dashboard_to_rules);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_dashboardFragment_to_editUserFragment);
            }
        });

        league.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_dashboardFragment_to_leagueDetailsFragment);
            }
        });

        return view;
    }
//
//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        inflater.inflate(R.menu.back_menu,menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.back_btn:
//                if(view != null) {
//                    Navigation.findNavController(view).popBackStack();
//                }
//                break;
//            default:
//
//        }
//        return super.onOptionsItemSelected(item);
//
//    }
}