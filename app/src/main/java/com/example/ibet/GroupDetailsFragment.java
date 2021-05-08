package com.example.ibet;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibet.model.Group.Group;
import com.example.ibet.model.Group.GroupViewModel;
import com.example.ibet.model.Match.Match;
import com.example.ibet.model.Match.MatchAdapter;
import com.example.ibet.model.Match.MatchViewModel;
import com.example.ibet.model.Model;
import com.example.ibet.model.Team.Team;
import com.example.ibet.model.User.User;
import com.example.ibet.model.User.UserAdapter;
import com.example.ibet.model.User.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class GroupDetailsFragment extends Fragment {

    View view;

    //Button upcoming_matches;

    TextView group_name;

    String group_id;
    Group currentGroup;

    public RecyclerView usersList_rv;
    UserAdapter userAdapter;
    private GroupViewModel groupViewModel;
    List<User> usersList = new ArrayList<User>();
    private UserViewModel userViewModel;

    BottomNavigationView bottomNav;

    Dialog myDialog;

    String teamName;

    User currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_group_details, container, false);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        bottomNav = view.findViewById(R.id.bottom_navigation_bar);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        myDialog = new Dialog(view.getContext());

        usersList_rv = view.findViewById(R.id.group_details_rv);
        usersList_rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        usersList_rv.setLayoutManager(layoutManager);
        userAdapter = new UserAdapter();

        group_name= view.findViewById(R.id.group_details_title);

        currentUser = new User();
        group_id = GroupDetailsFragmentArgs.fromBundle(getArguments()).getGroupID();
        group_name.setText(GroupDetailsFragmentArgs.fromBundle(getArguments()).getGroupName());

        Model.instance.getGroupData(group_id, new Model.GroupListener() {
            @Override
            public void onComplete(boolean result, Group group) {
                currentGroup = group;
            }
        });

        Model.instance.getGroupUsers(group_id,new Model.UserListListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(List<User> users) {
                usersList = users;
                usersList.sort(new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        int a = Integer.parseInt(o1.getScore());
                        int b = Integer.parseInt(o2.getScore());
                        return b-a;
                    }
                });
                userAdapter.setUsersData(usersList);
                usersList_rv.setAdapter(userAdapter);
            }
        });

        userAdapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User user, View view) {
                GroupDetailsFragmentDirections.ActionGroupDetailsFragmentToUserBetsPopUpFragment action = GroupDetailsFragmentDirections.
                        actionGroupDetailsFragmentToUserBetsPopUpFragment(user.getId(),group_id,user.getUserName());
                Navigation.findNavController(view).navigate(action);
            }
        });

        Model.instance.getCurrentUserDetails(new Model.UserDetailsListener() {
            @Override
            public void onComplete(User user) {
                currentUser = user;
                if(!isAdmin()){
                    bottomNav.getMenu().findItem(R.id.nav_group_share).setVisible(false);
                }
            }
        });

        return view;
    }



    public void ShowPopup(View v){

        Button confirmBtn;
        myDialog.setContentView(R.layout.fragment_league_winner);
        confirmBtn = (Button) myDialog.findViewById(R.id.popup_winner_btn);

        Spinner leagueDropDown;
        leagueDropDown = myDialog.findViewById(R.id.winner_teams_list);


        //create a list of items for the spinner.
        String[] leagues = new String[]{"NBA","A","B","C"};
        List<String> teams = new ArrayList<>();
        teams.add("Select winning team");
        Model.instance.getTeamData(new Model.TeamDataListener() {
            @Override
            public void onComplete(ArrayList<Team> teamData) {
                for(int i=0;i<teamData.size();i++){
                    Team t = teamData.get(i);
                    teams.add(t.getTeamName());
                }
            }
        });
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, teams);
        //set the spinners adapter to the previously created one.
        leagueDropDown.setAdapter(adapter);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamName = leagueDropDown.getSelectedItem().toString();
                if (teamName.equals("Select winning team")) {
                    myDialog.dismiss();
                }else{
                    Model.instance.winningTeamBet(group_id, teamName, new Model.SuccessListener() {
                        @Override
                        public void onComplete(boolean result) {
                            if(result){
                                Toast.makeText(getActivity(), "Successful Bet on: " + teamName, Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    myDialog.dismiss();
                }

            }
        });
        myDialog.show();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_group_main_feed:
                    if (view != null) {
                        Navigation.findNavController(view).navigate(R.id.action_groupDetailsFragment_to_mainFeedFragment);
                    }
                    break;
                case R.id.nav_group_bet:
                    if (view != null) {
                        GroupDetailsFragmentDirections.ActionGroupDetailsToUpcomingMatches action = GroupDetailsFragmentDirections.actionGroupDetailsToUpcomingMatches(group_id);
                        Navigation.findNavController(view).navigate(action);
                    }
                    break;
                case R.id.nav_group_league:
                    if(view!=null){
                        Navigation.findNavController(view).navigate(R.id.action_groupDetailsFragment_to_leagueDetailsFragment);
                    }
                    break;
                case R.id.nav_group_share:
                    if(view != null) {
                        Model.instance.shareCode(currentGroup, new Model.GroupListener() {
                            @Override
                            public void onComplete(boolean result, Group group) {
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, group.getShareCode());
                                sendIntent.setType("text/plain");

                                Intent shareIntent = Intent.createChooser(sendIntent, null);
                                startActivity(shareIntent);
                            }
                        });
                    }
                    break;
                case R.id.nav_group_team_pick:
                    if(view!=null){
                        ShowPopup(view);
                    }
                    break;

                default:

            }
            return true;
        }
    };

    public boolean isAdmin(){
        if(currentUser.getId().equals(currentGroup.getAdmin_id())){
            return true;
        }
        else{
            return false;
        }
    }
}