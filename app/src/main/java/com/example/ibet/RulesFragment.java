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


public class RulesFragment extends Fragment {

    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;
    TextView textView8;
    TextView textView9;
    TextView textView10;
    TextView textView11;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_rules, container, false);
        setHasOptionsMenu(true);

        textView1 = view.findViewById(R.id.rules_text1);
        textView2 = view.findViewById(R.id.rules_text2);
        textView3 = view.findViewById(R.id.rules_text3);
        textView4 = view.findViewById(R.id.rules_text4);
        textView5 = view.findViewById(R.id.rules_text5);
        textView6 = view.findViewById(R.id.rules_text6);
        textView7 = view.findViewById(R.id.rules_text7);
        textView8 = view.findViewById(R.id.rules_text8);
        textView9 = view.findViewById(R.id.rules_text9);
        textView10 = view.findViewById(R.id.rules_text10);
        textView11 = view.findViewById(R.id.rules_text11);

        textView1.setText("iBet rules:\n");

        textView2.setText("Match result: scoring system\n");

        textView3.setText("For each match, points are awarded for:\n" +
                "\n" +
                "- Match result - predict the correct winner or loser in the end of the match: +3 points\n" + "\n" +
                "- Half time result - predict the correct winner or loser in the end of the second half: +1 points\n" + "\n" +
                "- Over/under score - predict whether the total score is going to be over or under a certain given number: +2 points" + "\n");

        textView4.setText(
                "A guide to the number of points which can be earned for each prediction is displayed under each potential outcome. \n" +
                "Your points are then updated live at the end of the match.\n" +
                "\n" +
                "Predictions can be changed at any point up to the scheduled kick-off time of the match in question.\n" +
                "\n" +
                "For example: Player X bet on Team A vs Team B:\n" +
                "\n" +
                "- Match result - Team A wins\n" +
                "- Half time result - Team A wins\n" +
                "- Over/under score - under 150\n" +
                "\n" +
                "The result of the game: team A won the game (+3 points), but team B was leading at half time. The final score was 90:70 (90+70=160).\n" +
                "Player X will be given only 3 points because team B was leading at half time, not team A, and the score was over 150.\n");

        textView5.setText("Groups\n");

        textView6.setText("A key aspect of the game is the opportunity to compete against friends, colleagues and players from around the globe in private groups.\n" +
                "Private groups are the heart and soul of the game where you can compete against your friends, family and colleagues. \n" +
                "Simply join or create a group and then send out the unique code to allow others to join.\n" +
                "A private group lasts for the entire tournament – you can invite anyone to your group by sharing the unique code or link either via email or on social media.\n");

        textView7.setText("Changing the scoring system\n");

        textView8.setText("As said above, the points awarded for: match result, half time result, over/under score.\n" +
                "When creating a new betting group, you will be able to change the different value of the scoring system to whatever you prefer. \n" +
                "For example: Player X created a new group with this scoring system:\n");

        textView9.setText("- Match result - +1 points\n" +
                "- Half time result - +0 points\n" +
                "- Over/under score - +5 points");

        textView10.setText("\n Contact us");

        textView11.setText("\n If you have any questions about iBet rules that are not included in the information above, \n" +
                "feel free to email [**ibet@gmail.com**](mailto:ibet@gmail.com) and we will get back to you as soon as we can.");

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
                    Navigation.findNavController(view).navigate(R.id.action_rulesFragment_to_myProfile);
                }
                break;
            default:

        }
        return super.onOptionsItemSelected(item);

    }
}