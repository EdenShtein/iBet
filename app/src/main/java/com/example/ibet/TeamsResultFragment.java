package com.example.ibet;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ibet.model.Model;
import com.example.ibet.model.Team.Team;

import java.util.ArrayList;


public class TeamsResultFragment extends Fragment {

   View view;

    Button button;
    TableLayout table;

    ProgressBar pb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_teams_result, container, false);


        //button=view.findViewById(R.id.teamsresults_button);
        table = view.findViewById(R.id.table);
        pb=view.findViewById(R.id.teamsresults_pb);


//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        pb.setVisibility(View.VISIBLE);
        initTable();
        return view;
    }

    public void initTable(){
        Model.instance.getAlgoResult(new Model.TeamDataListener() {
            @Override
            public void onComplete(ArrayList<Team> teamData) {

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {

                        TableRow tbrow0 = new TableRow(getActivity().getApplicationContext());
                        TextView tr0 = new TextView(getActivity().getApplicationContext());
                        tr0.setText(" Team Name ");
                        tr0.setTextColor(Color.WHITE);
                        tbrow0.addView(tr0);
                        TextView tr1 = new TextView(getActivity().getApplicationContext());
                        tr1.setText(" Wins ");
                        tr1.setTextColor(Color.WHITE);
                        tbrow0.addView(tr1);
                        TextView tr2 = new TextView(getActivity().getApplicationContext());
                        tr2.setText(" Losses ");
                        tr2.setTextColor(Color.WHITE);
                        tbrow0.addView(tr2);
                        TextView tr3 = new TextView(getActivity().getApplicationContext());
                        tr3.setText(" Eliminated? ");
                        tr3.setTextColor(Color.WHITE);
                        tbrow0.addView(tr3);
                        table.addView(tbrow0);

                        for(int i=0;i<teamData.size();i++)
                        {
                            TableRow row= new TableRow(getActivity().getApplicationContext());
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            row.setLayoutParams(lp);

                            String teamName = teamData.get(i).getTeamName();
                            TextView name = new TextView(getActivity().getApplicationContext());
                            name.setText(teamName);
                            name.setGravity(Gravity.LEFT);
                            row.addView(name);

                            String wins = teamData.get(i).getWins();
                            TextView win = new TextView(getActivity().getApplicationContext());
                            win.setText(wins);
                            win.setGravity(Gravity.CENTER);
                            row.addView(win);

                            String losses = teamData.get(i).getLosses();
                            TextView lose = new TextView(getActivity().getApplicationContext());
                            lose.setText(losses);
                            lose.setGravity(Gravity.CENTER);
                            row.addView(lose);

                            Boolean isEliminated = teamData.get(i).getEliminated();
                            TextView eliminated = new TextView(getActivity().getApplicationContext());
                            eliminated.setText(String.valueOf(isEliminated));
                            eliminated.setGravity(Gravity.CENTER);
                            row.addView(eliminated);


                            table.addView(row,i);


                        }
                        pb.setVisibility(View.INVISIBLE);
//                        button.setVisibility(View.INVISIBLE);
//                        button.setEnabled(false);
                    }
                });
            }
        });
    }
}